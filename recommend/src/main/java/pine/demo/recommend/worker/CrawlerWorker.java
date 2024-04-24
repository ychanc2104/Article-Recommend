package pine.demo.recommend.worker;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;
import pine.demo.recommend.elasticsearch.repository.ArticleRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CrawlerWorker {
    private final ArticleRepository articleRepository;
    private final DateParserWorker dateParserWorker;
    private final String seed = "https://www.chinatimes.com";
    private final String website = "ctnew";

    public void ctnewsCrawler() throws IOException {
        Connection connection = this.setHeader(Jsoup.connect(seed));
        Document doc = connection.get();
        HashSet<String> categoryUrls = new HashSet<>();

        for (Element page : doc.select("div.main-nav-wrapper nav ul li.main-nav-item")) {
            Elements links = page.select("a[href]");
            if (links.isEmpty())  continue;

            links.stream()
                    .filter(item -> this.isAllowed(item.attr("href")))
                    .findFirst()
                    .ifPresent(element -> categoryUrls.add(this.cleanHref(element.attr("href"))));
        }

        List<ArticleDo> articleDoList = new ArrayList<>();

        for (String categoryUrl : categoryUrls) {
            for (int page=1; page <= 10; page++) {
                String urlPage = categoryUrl.substring(0, categoryUrl.length()-1) + "?page=" + page;
                Document pageDoc = this.setHeader(Jsoup.connect(urlPage)).get();
                Elements articleList = pageDoc.select("ul.vertical-list li div div div.col");

                articleList.forEach(article -> articleDoList.add(this.convert(article)));
//                for (Element article : articleList) {
//                    articleDoList.add(this.convert(article));
//                }
            }
        }

        List<ArticleDo> res = (List<ArticleDo>) articleRepository.saveAll(articleDoList);

        int xxxx = 0;
    }

    private ArticleDo convert(Element article) {
        ArticleDo res = new ArticleDo();
        try {
            String articleId = this.getAttr(article, "h3 a", "href");
            String category = this.getText(article, "div div.category");
            String title = this.getText(article, "h3");
            String url = seed + articleId;
            String introduction = this.getText(article, "p");
            String content = "";
            Date articleDate = dateParserWorker.toDate(this.getAttr(article, "div time", "datetime"));

            res.setArticleId(articleId);
            res.setWebsite(website);
            res.setCategory(category);
            res.setTitle(title);
            res.setUrl(url);
            res.setIntroduction(introduction);
            res.setContent(content);
            res.setArticleDate(articleDate);
            res.setCreatedDate(new Date());
            res.setUpdatedDate(new Date());
            res.setContent(this.crawlNewsContent(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    private String crawlNewsContent(String url) throws IOException {
        Document articleDoc = this.setHeader(Jsoup.connect(url)).get();

        Elements contents = articleDoc.select("div.article-body p");
        StringBuilder contentBuilder = new StringBuilder();
        for (Element content : contents) {
            String p = content.text();
            contentBuilder.append(p);
        }

        return contentBuilder.toString();
    }

    private String getAttr(Element element, String cssQuery, String attr) {
        return element.select(cssQuery).first() == null ? "" : element.select(cssQuery).first().attr(attr);
    }

    private String getText(Element element, String cssQuery) {
        return element.select(cssQuery).first() == null ? "" : element.select(cssQuery).first().text();
    }

    private boolean isAllowed(String href) {
        String allowRegex = ".*www.chinatimes.com/.*/total/.*";
        String NotAllowRegex = ".*www.chinatimes.com/realtimenews.*";

        return href.matches(allowRegex) && !href.matches(NotAllowRegex);
    }

    private String cleanHref(String href) {
        return "https:" + href;
    }

    public Connection setHeader(Connection conn) {
        conn.header("Host", "www.chinatimes.com")
            .header("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .header("Connection", "keep-alive")
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");

        return conn;
    }

}

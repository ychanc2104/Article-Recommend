package pine.demo.recommend.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pine.demo.recommend.elasticsearch.repository.ArticleRepository;
import pine.demo.recommend.worker.CrawlerWorker;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
public class CrawlerTest {

    @Autowired
    private CrawlerWorker crawlerWorker;

    @Autowired
    private ArticleRepository articleRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testSaveArticle() throws ParseException, IOException {
        crawlerWorker.ctnewsCrawler();
//        String seed = "https://www.chinatimes.com/?chdtv";
//
//        Document doc = Jsoup.connect(seed)
//                .header("Host", "www.chinatimes.com")
//                .header("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.5,en;q=0.3")
//                .header("Connection", "keep-alive")
//                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
//                .get();
//
//        HashSet<String> categoryUrls = new HashSet<>();
////        String allowRegex = ".*www.chinatimes.com.*";
//
//        for (Element page : doc.select("div.main-nav-wrapper nav ul li.main-nav-item")) {
//            Elements links = page.select("a[href]");
//            String href = links.first().attr("href");
//
//            if (!links.isEmpty() && href.matches(".*www.chinatimes.com.*")) {
//                categoryUrls.add(href);
//            }
//            System.out.println("Text inside <a>: " + href);
//        }
//
//        for (String url : categoryUrls) {
//
//        }

        int x = 0;
    }

}

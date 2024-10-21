package pine.demo.recommend.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;
import pine.demo.recommend.elasticsearch.repository.ArticleRepository;
import pine.demo.recommend.worker.ElasticSearchConnectionWorker;
import pine.demo.recommend.worker.ArticleTrieWorker;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class ElasticsearchTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private ElasticSearchConnectionWorker elasticSearchConnectionWorker;
    @Autowired
    private ArticleTrieWorker articleTrieWorker;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testSaveArticle() throws ParseException {
        ArticleDo res = new ArticleDo();

        res.setArticleId("/realtimenews/20240421002366-260405");
        res.setWebsite("中時");
        res.setCategory("時事");
        res.setTitle("響應地球日新北環境季起跑 雲仙樂園綠色遊程走讀南勢溪");
        res.setUrl("https://www.chinatimes.com/realtimenews/20240421002366-260405?chdtv");
        res.setIntroduction("4月22日為世界地球日，新北市環保局今與慈濟基金會合辦「2024世界地球日─多一塑不如少一塑」活動，同步宣布「2024新北環境季」正式起跑！環境季由新北環保局串聯4");
        res.setContent("");
        res.setArticleDate(sdf.parse("2024-03-03 09:10:00"));
        res.setCreatedDate(new Date());
        res.setUpdatedDate(new Date());

        ArticleDo data = articleRepository.saveIfUnique(res);

        int x = 0;
    }

    @Test
    public void testSearchArticle() throws IOException {
//        List<ArticleDo> data = articleRepository.findAllByCategory("時事");
//        List<ArticleDo> data2 = articleRepository.findAllByTitleMatches("山豬");

//        SearchHits<ArticleDo> data = articleRepository.findByCategory("時事");

//        List<String> data2 = articleTrieWorker.tokenize("上課時間卻在市場買雞蛋？校長聲明自清：人身攻擊");
//        AnalyzeResponse data2 = elasticSearchConnectionWorker.getClientInstance()
//                .indices()
//                .analyze(s -> s
//                        .index("article")
//                        .analyzer("ik_smart").text("上課時間卻在市場買雞蛋？校長聲明自清：人身攻擊"));

//        Optional<BookDo> data2 = bookRepository.findById(data.getSearchHits().get(0).getId());
//        Iterable<ArticleDo> data3 = articleRepository.findAll();


        List<ArticleDo> data = articleRepository.findAllByTitleMatches("中國");
        List<String> allWords = new ArrayList<>();
        data.forEach(item -> allWords.addAll(articleTrieWorker.tokenize(item.getTitle())));
        Map<String, Integer> counter = new HashMap<>();

        for (String word : allWords) {
            counter.put(word, counter.getOrDefault(word, 0) + 1);
        }
        int x = 0;
    }

}

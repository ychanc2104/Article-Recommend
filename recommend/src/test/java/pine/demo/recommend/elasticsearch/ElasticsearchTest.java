package pine.demo.recommend.elasticsearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.SearchHits;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;
import pine.demo.recommend.elasticsearch.repository.ArticleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class ElasticsearchTest {
    @Autowired
    private ArticleRepository articleRepository;

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
    public void testSearchArticle() {

        SearchHits<ArticleDo> data = articleRepository.findByCategory("時事");

//        Optional<BookDo> data2 = bookRepository.findById(data.getSearchHits().get(0).getId());

        int x = 0;
    }

}

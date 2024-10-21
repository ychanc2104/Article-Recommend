package pine.demo.recommend.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;
import pine.demo.recommend.elasticsearch.repository.ArticleRepository;
import pine.demo.recommend.neo4j.entity.ArticleTrieNodeDo;
import pine.demo.recommend.neo4j.repository.ArticleTrieRepository;
import pine.demo.recommend.worker.ArticleTrieWorker;
import pine.demo.recommend.worker.dto.TrieNodeDto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootTest
public class Neo4jTest {
    @Autowired
    private ArticleTrieRepository articleTrieRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTrieWorker articleTrieWorker;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testSearchTrie() throws IOException {
        TrieNodeDto root = articleTrieWorker.getTrieRoot();

//        articleTrieWorker.buildLocalTrie(root);
        List<String> res =  articleTrieWorker.searchTopK("中國", 10);
        List<String> res1 =  articleTrieWorker.searchTopK("台", 10);

        List<String> res2 =  articleTrieWorker.searchTopK("", 10);

        int x = 0;
    }

    @Test
    public void testBuildLocalTrie() throws IOException {
        TrieNodeDto root = articleTrieWorker.getTrieRoot();

        articleTrieWorker.initTrie(root);

        int x = 0;
    }

    @Test
    public void testBuildArticleTrie() throws IOException {
        articleTrieRepository.deleteAll();
        TrieNodeDto root = articleTrieWorker.getTrieRoot();
//        List<ArticleDo> data = articleRepository.findAll();
        List<ArticleDo> data = articleRepository.findAllByTitleMatches("中");
//        List<ArticleDo> data = articleRepository.findAllByCategory("時事");
        articleTrieWorker.saveNeo4jArticleTrie(data, 300);
        articleTrieWorker.save(root.getNode());

        int x = 0;
    }

    @Test
    public void testSaveRoot() throws ParseException {
        ArticleTrieNodeDo root = new ArticleTrieNodeDo();

        ArticleTrieNodeDo data = articleTrieRepository.save(root);

        int x = 0;
    }

    @Test
    public void testSaveTrie() throws ParseException {
        ArticleTrieNodeDo root = new ArticleTrieNodeDo();


        ArticleTrieNodeDo res = new ArticleTrieNodeDo();
        res.setCharacter('a');
        ArticleTrieNodeDo res2 = new ArticleTrieNodeDo();
        res2.setCharacter('p');
        ArticleTrieNodeDo res3 = new ArticleTrieNodeDo();
        res3.setCharacter('p');
        res3.setIsWord(true);
        res3.setCount(res3.getCount() + 1);

        root.getDirectors().add(res);
        res.getDirectors().add(res2);
        res2.getDirectors().add(res3);

        ArticleTrieNodeDo data = articleTrieRepository.save(root);

        int x = 0;
    }

    @Test
    public void testQueryTrie() throws ParseException {
//        ArticleTrieNodeDo neo4jRoot = articleTrieWorker.getNeo4jRoot();
//        TrieNodeDto root = articleTrieWorker.getTrieRoot();
        articleTrieRepository.deleteAll();

        int x = 0;
    }

    @Test
    public void testTrie() throws IOException {
        articleTrieRepository.deleteAll();

        TrieNodeDto root = articleTrieWorker.getTrieRoot();
//        ArticleTrieNodeDo data = articleTrieRepository.save(new ArticleTrieNodeDo());
        articleTrieWorker.add("apple", 1);
        articleTrieWorker.add("applications", 3);
        articleTrieWorker.add("ace", 2);
//        articleTrieWorker.add("banana");
//        articleTrieWorker.add("bomb");


        int x = 0;
    }

}

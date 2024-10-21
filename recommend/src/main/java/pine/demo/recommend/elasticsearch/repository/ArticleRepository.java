package pine.demo.recommend.elasticsearch.repository;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.CrudRepository;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;

import java.util.List;


public interface ArticleRepository extends CrudRepository<ArticleDo, String> {
    SearchHits<ArticleDo> findByArticleId(String articleId);
    List<ArticleDo> findAll();
    List<ArticleDo> findAllByCategory(String category);
    List<ArticleDo> findAllByTitleMatches(String title);

    default ArticleDo saveIfUnique(ArticleDo articleDo) {
        SearchHits<ArticleDo> data = findByArticleId(articleDo.getArticleId());

        if (data.getSearchHits().isEmpty()) {
            return save(articleDo);
        }

        return null;
    };
}

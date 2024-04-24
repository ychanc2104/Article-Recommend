package pine.demo.recommend.elasticsearch.repository;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.CrudRepository;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;


public interface ArticleRepository extends CrudRepository<ArticleDo, String> {
    SearchHits<ArticleDo> findByArticleId(String articleId);

    SearchHits<ArticleDo> findByCategory(String category);

    default ArticleDo saveIfUnique(ArticleDo articleDo) {
        SearchHits<ArticleDo> data = findByArticleId(articleDo.getArticleId());

        if (data.getSearchHits().isEmpty()) {
            return save(articleDo);
        }

        return null;
    };
}

package pine.demo.recommend.neo4j.repository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pine.demo.recommend.neo4j.entity.ArticleTrieNodeDo;


public interface ArticleTrieRepository extends CrudRepository<ArticleTrieNodeDo, Long> {
//    @Query("MATCH (n:ArticleTrieNodeDo)-[r]->(c) WHERE n.character IS NULL OPTIONAL MATCH (c)-[d]->(directors) RETURN n, collect(r), collect(c), collect(directors)")
    ArticleTrieNodeDo findTopByCharacterIsNull();

}

package pine.demo.recommend.neo4j.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@Node("ArticleTrie")
public class ArticleTrieNodeDo {

    @Id
    @GeneratedValue
    private Long id;

    private Character character;

    private Integer count = 0;

    private Boolean isWord = false;

    @Relationship(type = "children", direction = Relationship.Direction.OUTGOING)
    private List<ArticleTrieNodeDo> directors = new ArrayList<>();

}

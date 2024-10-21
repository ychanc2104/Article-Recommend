package pine.demo.recommend.worker.dto;

import lombok.Data;
import pine.demo.recommend.neo4j.entity.ArticleTrieNodeDo;

import java.util.HashMap;
import java.util.Map;

@Data
public
class TrieNodeDto {

    private Map<Character, TrieNodeDto> children = new HashMap<>();

    private ArticleTrieNodeDo node = new ArticleTrieNodeDo();
}

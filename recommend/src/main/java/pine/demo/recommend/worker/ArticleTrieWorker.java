package pine.demo.recommend.worker;

import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;
import pine.demo.recommend.neo4j.entity.ArticleTrieNodeDo;
import pine.demo.recommend.neo4j.repository.ArticleTrieRepository;
import pine.demo.recommend.utils.StringUtils;
import pine.demo.recommend.worker.dto.HeapNodeDto;
import pine.demo.recommend.worker.dto.TrieNodeDto;
import pine.demo.recommend.worker.dto.comparator.WordFrequencyComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArticleTrieWorker {
    private final ArticleTrieRepository articleTrieRepository;
    private final ElasticSearchConnectionWorker elasticSearchConnectionWorker;
    private TrieNodeDto trie = null;

    public TrieNodeDto getTrieRoot() {
        if (trie == null) {
            trie = new TrieNodeDto();
            trie.setNode(this.getNeo4jRoot()); // init from neo4j
            this.initTrie(trie);
        }

        return trie;
    }

    public ArticleTrieNodeDo getNeo4jRoot() {
        ArticleTrieNodeDo root = articleTrieRepository.findTopByCharacterIsNull();
//        ArticleTrieNodeDo root = articleTrieRepository.findById(0L).orElse(null);

        if (root == null) return this.insertNeo4jRoot();

        return root;
    }

    public ArticleTrieNodeDo insertNeo4jRoot() {
        return articleTrieRepository.save(new ArticleTrieNodeDo());
    }

    public void saveNeo4jArticleTrie(List<ArticleDo> data, int chunkSize) {
        int n = data.size();
        int m = (int) Math.ceil((double) n / chunkSize);
        for (int i = 0; i < m; i++) {
            this.saveNeo4jArticleTrie(data.subList(i * chunkSize, Math.min((i + 1) * chunkSize, n)));
        }
    }

    public void saveNeo4jArticleTrie(List<ArticleDo> data) {
        data.stream()
                .flatMap(item -> this.tokenize(item.getTitle()).stream())
                .collect(Collectors.toMap(Function.identity(), _ -> 1, Integer::sum))
                .forEach(this::add);
    }

    public List<String> searchTopK(String prefix, int topK) {
        if (prefix == null) return new ArrayList<>();

        PriorityQueue<HeapNodeDto> heap = new PriorityQueue<>(new WordFrequencyComparator());
        TrieNodeDto root = this.getTrieRoot();
        for (char c : prefix.toCharArray()) {
            if (!root.getChildren().containsKey(c)) break;

            root = root.getChildren().get(c);
        }
        // get all words
        List<Character> path = new ArrayList<>();
        for (TrieNodeDto child : root.getChildren().values()) {
            this.dfs(path, child, heap, topK);
        }

        List<String> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(prefix + heap.poll().getWord());
        }

        return res.reversed();
    }

    public void dfs(List<Character> path, TrieNodeDto node, PriorityQueue<HeapNodeDto> results, int topK) {
        if (node == null) return;

        path.add(node.getNode().getCharacter());

        if (node.getNode().getIsWord()) {
            results.add(new HeapNodeDto(StringUtils.listToString(path), node.getNode().getCount()));

            if (results.size() > topK) results.poll();
        }

        for (TrieNodeDto child : node.getChildren().values()) {
            this.dfs(path, child, results, topK);
        }

        path.removeLast();
    }

    public void add(String token, Integer count) {
        TrieNodeDto root = this.getTrieRoot();
        ArticleTrieNodeDo prev = root.getNode();

        for (Character character : token.toCharArray()) {
            if (!root.getChildren().containsKey(character)) {
                TrieNodeDto newNode = new TrieNodeDto();
                newNode.setNode(this.initNeo4jNode(character));
                root.getChildren().put(character, newNode);
                prev.getDirectors().add(newNode.getNode());
            }
            root = root.getChildren().get(character);
            prev = root.getNode();

//            if (root.getChildren().containsKey(character)) {
//                root = root.getChildren().get(character);
//            } else {
//                TrieNodeDto newNode = new TrieNodeDto();
//                newNode.setNode(this.initNeo4jNode(character));
//                root.getChildren().put(character, newNode);
//                root = newNode;
//                prev.getDirectors().add(newNode.getNode());
//            }
//
//            prev = root.getNode();
        }

        root.getNode().setIsWord(true);
        root.getNode().setCount(root.getNode().getCount() + count);
    }

    public ArticleTrieNodeDo save(ArticleTrieNodeDo neo4jRoot) {
        return articleTrieRepository.save(neo4jRoot);
    }

    public void initTrie(TrieNodeDto root) {
        if (root == null) return;

        Map<Character, TrieNodeDto> children = root.getNode().getDirectors()
                .stream()
                .collect(Collectors.toMap(
                        ArticleTrieNodeDo::getCharacter,
                        this::convert)
                );
        root.setChildren(children);
        children.forEach((key, value) -> this.initTrie(value));
    }

    public ArticleTrieNodeDo initNeo4jNode(Character character) {
        ArticleTrieNodeDo res = new ArticleTrieNodeDo();
        res.setCharacter(character);

        return res;
    }

    public List<String> tokenize(String sentence) {
        try {
            return elasticSearchConnectionWorker.getClientInstance()
                    .indices()
                    .analyze(s -> s
                            .index("article")
                            .analyzer("ik_smart")
                            .text(sentence))
                    .tokens()
                    .stream()
                    .map(AnalyzeToken::token)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return List.of("");
        }
    }

    private TrieNodeDto convert(ArticleTrieNodeDo input) {
        TrieNodeDto res = new TrieNodeDto();
        res.setNode(input);

        return res;
    }
}

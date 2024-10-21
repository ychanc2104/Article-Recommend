package pine.demo.recommend;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.ResponseEntity;
import pine.demo.recommend.elasticsearch.entity.ArticleDo;
import pine.demo.recommend.elasticsearch.repository.ArticleRepository;
import pine.demo.recommend.worker.ArticleTrieWorker;
import pine.demo.recommend.worker.ElasticSearchConnectionWorker;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class AITest {

//    @Autowired
//    private OpenAiApi openAiApi;

    @Autowired
    private OpenAiChatClient chatClient;

    @Test
    public void testChat() throws ParseException {

        Flux<ChatResponse> data = chatClient.stream(new Prompt(new UserMessage("Hello world")));

        int x = 0;
    }

}

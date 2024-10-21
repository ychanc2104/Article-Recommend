package pine.demo.recommend.worker;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.HealthResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ElasticSearchConnectionWorker {
    private ElasticsearchClient client = null;

    public ElasticsearchClient getClientInstance() {
        if (client == null) {
            RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            client = new ElasticsearchClient(transport);
        }

        return client;
    }

    public HealthResponse getHealth() throws IOException {
        return this.getClientInstance().cat().health();
    }

}

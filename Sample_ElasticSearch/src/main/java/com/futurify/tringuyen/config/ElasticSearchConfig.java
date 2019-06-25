package com.futurify.tringuyen.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

	@Value("${elasticsearch.host:localhost}")
	private String host;

	@Value("${elasticsearch.port:9200}")
	private int port;

	@Bean(name = "elasticSearch", destroyMethod = "close")
	public RestHighLevelClient restClient() {
		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));

		RestHighLevelClient client = new RestHighLevelClient(builder);

		return client;
	}

}
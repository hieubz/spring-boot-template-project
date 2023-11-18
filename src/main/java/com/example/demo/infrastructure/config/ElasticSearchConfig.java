package com.example.demo.infrastructure.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.demo.infrastructure.repository.es")
//public class ElasticSearchConfig extends ElasticsearchConfigurationSupport {
//
//  @Value("${spring.elasticsearch.host}")
//  private String esHost;
//
//  @Value("${spring.elasticsearch.port}")
//  private Integer esPort;
//
//  @Bean(name = "elasticsearchClient")
//  public ElasticsearchClient elasticsearchClient() {
//    RestClient httpClient = RestClient.builder(new HttpHost(esHost, esPort)).build();
//    ElasticsearchTransport transport =
//        new RestClientTransport(httpClient, new JacksonJsonpMapper());
//    return new ElasticsearchClient(transport);
//  }
//
//  @Bean("elasticsearchTemplate")
//  public ElasticsearchTemplate elasticsearchTemplate(
//      @Qualifier("elasticsearchClient") ElasticsearchClient client,
//      ElasticsearchConverter elasticsearchConverter) {
//    ElasticsearchTemplate template = new ElasticsearchTemplate(client, elasticsearchConverter);
//    template.setRefreshPolicy(this.refreshPolicy());
//    return template;
//  }
//}

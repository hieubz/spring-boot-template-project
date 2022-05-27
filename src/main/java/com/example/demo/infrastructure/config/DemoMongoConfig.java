package com.example.demo.infrastructure.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.demo.infrastructure.repository.mongo.primary")
@EnableMongoAuditing
public class DemoMongoConfig {

  @Primary
  @Bean(name = "primary")
  @ConfigurationProperties(prefix = "spring.data.mongodb")
  public MongoProperties getDemoProperties() {
    return new MongoProperties();
  }

  @Primary
  @Bean(name = "mongoTemplate")
  public MongoTemplate demoMongoTemplate() {
    MongoDatabaseFactory mongoDatabaseFactory = demoFactory(getDemoProperties());
    return new MongoTemplate(mongoDatabaseFactory, mongoConverter(mongoDatabaseFactory));
  }

  @Bean
  @Primary
  public MongoDatabaseFactory demoFactory(final MongoProperties properties) {
    return new SimpleMongoClientDatabaseFactory(properties.getUri());
  }

  private MongoMappingContext initMappingContext() {
    MongoMappingContext mappingContext = new MongoMappingContext();
    mappingContext.setFieldNamingStrategy(new SnakeCaseFieldNamingStrategy());

    return mappingContext;
  }

  private MappingMongoConverter mongoConverter(MongoDatabaseFactory factory) {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);

    MappingMongoConverter mongoConverter =
        new MappingMongoConverter(dbRefResolver, initMappingContext());
    mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

    mongoConverter.afterPropertiesSet();
    return mongoConverter;
  }
}

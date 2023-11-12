package com.example.demo.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "RoDemoEntityManagerFactory",
    transactionManagerRef = "RoDemoTransactionManager",
    basePackages = {"com.example.demo.infrastructure.repository.mysql.read_only"})
public class RoDemoDataSourceConfig extends DataSourceConfig {
  public static final String PERSISTENCE_UNIT_NAME = "RoDemo";
  public static final String MODEL_PACKAGE = "com.example.demo.infrastructure.repository.mysql.entity";

  @Bean(name = "RoDemoDataSource")
  @ConfigurationProperties("spring.datasource-demo-ro")
  public DataSource roDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "RoDemoEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean roDemoEntityManagerFactory(
      EntityManagerFactoryBuilder entityManagerFactoryBuilder,
      @Qualifier("RoDemoDataSource") DataSource dataSource) {

    return entityManagerFactoryBuilder
        .dataSource(dataSource)
        .packages(MODEL_PACKAGE)
        .properties(JpaAdditionalPropertiesHelper.additionalProperties())
        .persistenceUnit(PERSISTENCE_UNIT_NAME)
        .build();
  }

  @Bean(name = "RoDemoTransactionManager")
  public PlatformTransactionManager roDemoTransactionManager(
      @Qualifier("RoDemoEntityManagerFactory") EntityManagerFactory dfEntityManagerFactory) {
    return new JpaTransactionManager(dfEntityManagerFactory);
  }
}

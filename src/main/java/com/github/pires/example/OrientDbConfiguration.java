package com.github.pires.example;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.orient.core.OrientObjectTemplate;
import org.springframework.data.orient.repository.config.EnableOrientRepositories;
import org.springframework.orm.orient.OrientObjectDatabaseFactory;
import org.springframework.orm.orient.OrientTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableOrientRepositories(basePackages = "com.github.pires.example.repository")
public class OrientDbConfiguration {

  @Bean
  public OrientObjectDatabaseFactory factory() {
    final OrientObjectDatabaseFactory factory = new OrientObjectDatabaseFactory();
    factory.setUrl("remote:localhost/orientdb-test");
    factory.setUsername("admin");
    factory.setPassword("admin");

    return factory;
  }

  @Bean
  public OrientTransactionManager transactionManager() {
    return new OrientTransactionManager(factory());
  }

  @Bean
  public OrientObjectTemplate objectTemplate() {
    return new OrientObjectTemplate(factory());
  }

  @PostConstruct
  public void registerEntities() {
    factory().db().getEntityManager().
        registerEntityClasses("com.github.pires.example.model");
  }

}

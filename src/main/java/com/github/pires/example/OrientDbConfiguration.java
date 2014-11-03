/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

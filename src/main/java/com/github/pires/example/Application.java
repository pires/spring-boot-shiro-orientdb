package com.github.pires.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

  public static void main(String... args) {
    new SpringApplicationBuilder()
        .sources(Application.class)
        .showBanner(false)
        .run(args);
  }

//  @Override
//  protected SpringApplicationBuilder configure(
//      SpringApplicationBuilder application) {
//    return application.sources(Application.class);
//  }

}

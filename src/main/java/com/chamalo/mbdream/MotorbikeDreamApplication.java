package com.chamalo.mbdream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
@PropertySource(value = "classpath:application.properties")
@PropertySource(value = "classpath:datasource.properties", ignoreResourceNotFound = true)
public class MotorbikeDreamApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MotorbikeDreamApplication.class, args);
    }
}

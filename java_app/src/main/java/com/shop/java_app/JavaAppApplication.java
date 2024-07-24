package com.shop.java_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavaAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaAppApplication.class, args);
    }
}

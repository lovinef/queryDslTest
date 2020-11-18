package com.example.dsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class DslApplication {
    public static void main(String[] args) {
        SpringApplication.run(DslApplication.class, args);
    }

}

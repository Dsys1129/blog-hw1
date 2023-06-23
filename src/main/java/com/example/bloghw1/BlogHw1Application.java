package com.example.bloghw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogHw1Application {

    public static void main(String[] args) {
        SpringApplication.run(BlogHw1Application.class, args);
    }

}

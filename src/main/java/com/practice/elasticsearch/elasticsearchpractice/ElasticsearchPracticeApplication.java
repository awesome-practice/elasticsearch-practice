package com.practice.elasticsearch.elasticsearchpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.practice.elasticsearch.elasticsearchpractice.repository"})
public class ElasticsearchPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchPracticeApplication.class, args);
    }


}

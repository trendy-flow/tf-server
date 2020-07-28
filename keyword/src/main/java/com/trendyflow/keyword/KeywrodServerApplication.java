package com.trendyflow.keyword;


import com.trendyflow.keyword.service.KeywordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class KeywrodServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeywrodServerApplication.class, args);
    }

}

package com.triple.clubmileage.common.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public class AutoConfig {

    @Configuration
    @EnableJpaAuditing
    public static class JpaConfig {

    }

    @Configuration
    @EnableMongoAuditing
    @RequiredArgsConstructor
    @EnableMongoRepositories
    public static class MongoConfig {

        private final MongoDatabaseFactory dbFactory;

        @Bean
        MongoTransactionManager transactionManager() {
            return new MongoTransactionManager(dbFactory);
        }
    }


}

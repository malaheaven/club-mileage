package com.triple.clubmileage.common.config;

import com.triple.clubmileage.repository.review.UserReviewHistoryRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public class AutoConfig {

    @Configuration
    @EnableJpaAuditing
    public static class JpaConfig {

    }

    @Configuration
    @EnableMongoAuditing
    @EnableMongoRepositories(basePackageClasses = UserReviewHistoryRepository.class)
    public static class MongoConfig {

    }

}

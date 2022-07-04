package com.triple.clubmileage.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


public class AutoConfig {

    @Configuration
    @EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class})
    public static class TestEmbeddedMongoConfig {

        private static final String CONNECTION_STRING = "mongodb://%s:%d";

        @Bean
        public MongoTemplate mongoTemplate() throws Exception {
            String ip = "localhost";
            int port = Network.getFreeServerPort();

            MongodConfig mongodConfig = MongodConfig.builder()
                    .version(Version.Main.V4_0)
                    .net(new Net(ip, port, Network.localhostIsIPv6()))
                    .build();

            MongodStarter starter = MongodStarter.getDefaultInstance();
            MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();

            MongoClient mongo = MongoClients.create(String.format(CONNECTION_STRING, ip, port));
            return new MongoTemplate(mongo, "clubmileage");
        }

    }


}

package com.shelter.animalback.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    @Bean
    public NewTopic topic() {
        return new NewTopic(topic, 1, (short) 1);
    }
}

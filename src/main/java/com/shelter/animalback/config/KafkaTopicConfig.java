package com.shelter.animalback.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.topic.publish}")
    private String publishTopic;

    @Value(value = "${spring.kafka.topic.listen}")
    private String listenTopic;

    @Bean
    public NewTopic publishTopic() {
        return new NewTopic(publishTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic listenerTopic() {
        return new NewTopic(listenTopic, 1, (short) 1);
    }
}

package com.shelter.animalback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class IntegrationConfig {

    @Bean("aiIntegration")
    public FancyAIIntegration getSuperAIModule() {
        return new FancyAIIntegration();
    }

    public class FancyAIIntegration {
        public Integer getLifeExpectancy() {
            int leftLimit = 7;
            int rightLimit = 18;
            return leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
        }
    }
}

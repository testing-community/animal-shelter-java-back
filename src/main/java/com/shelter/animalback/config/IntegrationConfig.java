package com.shelter.animalback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Random;

@Configuration
public class IntegrationConfig {

    @Bean("aiIntegration")
    public AIIntegration getSuperAIModule() {
        return new AIIntegration();
    }

    public class AIIntegration {
        public Integer getLifeExpectancy() {
            int leftLimit = 7;
            int rightLimit = 18;
            return leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
        }
    }
}

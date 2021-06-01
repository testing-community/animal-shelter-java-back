package com.shelter.animalback.test.config;

import com.shelter.animalback.config.IntegrationConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(
        basePackages = {"com.shelter.animalback"}
)
public class TestApplication {
    @MockBean
    private IntegrationConfig.FancyAIIntegration fancyAIIntegration;
}

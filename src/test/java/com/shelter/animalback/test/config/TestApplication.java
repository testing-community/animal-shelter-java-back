package com.shelter.animalback.test.config;

import com.shelter.animalback.config.IntegrationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan(
        basePackages = {"com.shelter.animalback"}
)
public class TestApplication {
    @MockBean
    private IntegrationConfig.FancyAIIntegration fancyAIIntegration;

    @Autowired
    public void configureSchema(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema-postgresql.sql"));

        DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);
    }
}

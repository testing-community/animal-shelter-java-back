package com.shelter.animalback.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
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
    @Autowired
    public void executeScripts(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("test-data.sql"));

        DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);
    }
}

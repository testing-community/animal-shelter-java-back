package com.shelter.animalback.integration.db.animal;

import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:integration-test.yml"})
@Testcontainers
public class AnimalDataBaseTest {
    @Container
    private static PostgreSQLContainer database = new PostgreSQLContainer("postgres:13.2");

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void createAnimal() {
        var animal = new AnimalDao("Hela", "Mestizo", "Female", true);
        var animalDb = animalRepository.save(animal);

        assertThat(animalDb, notNullValue());
        assertThat(animalDb.getName(), equalTo("ThisIsMyLongName"));
        assertThat(animalDb.getBreed(), equalTo("Mestizo"));
        assertThat(animalDb.getGender(), equalTo("Female"));
        assertThat(animalDb.isVaccinated(), equalTo(true));
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }
}

package com.shelter.animalback.test;

import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import com.shelter.animalback.test.config.TestApplication;
import com.shelter.animalback.test.config.TestContextInitializer;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
@ContextConfiguration(initializers = TestContextInitializer.class)
public class GetAnimalComponentTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AnimalRepository animalRepository;

    private AnimalDao animal;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void testGetAnimalSuccessfully() {
        MockMvcResponse response = RestAssuredMockMvc.get("/animals/Chepe").thenReturn();
        AnimalDto animalResponse = response.as(AnimalDto.class);

        assertThat(animalResponse.getName(), equalTo("Chepe"));
        assertThat(animalResponse.getBreed(), equalTo("French Bulldog"));
        assertThat(animalResponse.getGender(), equalTo("Male"));
        assertThat(animalResponse.isVaccinated(), equalTo(true));
    }
}

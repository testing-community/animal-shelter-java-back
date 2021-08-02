package com.shelter.animalback.test;

import com.shelter.animalback.config.IntegrationConfig;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import com.shelter.animalback.test.config.TestApplication;
import com.shelter.animalback.test.config.TestContextInitializer;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
@ContextConfiguration(initializers = TestContextInitializer.class)
public class GetAnimalComponentTest {

    @Autowired
    private IntegrationConfig.FancyAIIntegration fancyAIIntegration;

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

    @Test
    public void testGetAnimalIntegration() {
        // Arrange: Mock result for the integration call
        Mockito.when(fancyAIIntegration.getLifeExpectancy()).thenReturn(10);

        // Act: Make a GET request
        MockMvcResponse response = RestAssuredMockMvc.get("/animals/Chepe").thenReturn();
        AnimalDto animalResponse = response.as(AnimalDto.class);

        // Assert: validate response - mocked value should be returned
        assertThat(animalResponse.getLifeExpectancy(), is(10));
    }
}

package com.shelter.animalback.test;

import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetAnimalComponentTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        AnimalDao animal = new AnimalDao("Chepe", "French Bulldog", "Male", true);
        animalRepository.save(animal);
    }

    @Test
    public void testGetAnimalSuccessfully() {
        // Act - Make a POST request against the create animal endpoint
        MockMvcResponse response = RestAssuredMockMvc.get("/animals/Chepe").thenReturn();

        AnimalDto animalResponse = response.as(AnimalDto.class);

        // Assert - validate response: verify Animal fields
        assertThat(animalResponse.getName(), equalTo("Chepe"));
        assertThat(animalResponse.getBreed(), equalTo("French Bulldog"));
        assertThat(animalResponse.getGender(), equalTo("Male"));
        assertThat(animalResponse.isVaccinated(), equalTo(true));
    }
}

package com.shelter.animalback.component.api.animal;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SaveAnimalComponentTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void createAnimalSuccessful() throws JsonProcessingException {
        // Arrange - Instance animal with data for request body
        CreateAnimalBodyDto animal = new CreateAnimalBodyDto();
        animal.setName("Hela");
        animal.setBreed("Mestizo");
        animal.setGender("Female");
        animal.setVaccinated(true);

        String createAnimalRequestBody = new ObjectMapper().writeValueAsString(animal);

        // Act - Make a POST request against the create animal endpoint
        MockMvcResponse response = RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createAnimalRequestBody)
                .when()
                .post("/animals")
                .thenReturn();

        AnimalDto animalResponse = response.as(AnimalDto.class);

        // Assert - validate response: verify Animal fields
        assertThat(animalResponse.getName(), equalTo("Hela"));
        assertThat(animalResponse.getBreed(), equalTo("Mestizo"));
        assertThat(animalResponse.getGender(), equalTo("Female"));
        assertThat(animalResponse.isVaccinated(), equalTo(true));
        assertThat(animalResponse.getId(), notNullValue());

        // Assert - Verify db creation.
    }
}

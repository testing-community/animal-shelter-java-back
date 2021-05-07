package com.shelter.animalback.component.api.animal;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SaveAnimalTest {
    public AnimalDto animalResponse;

    @Test
    public void createAnimalSuccessful() throws JsonProcessingException {
        // Arrange - Instance animal with data for request body
        CreateAnimalBodyDto animal = new CreateAnimalBodyDto();
        animal.setName("ThisIsMyLongName");
        animal.setBreed("Mestizo");
        animal.setGender("Female");
        animal.setVaccinated(true);

        String createAnimalRequestBody = new ObjectMapper().writeValueAsString(animal);

        // Act - Make a POST request against the create animal endpoint
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createAnimalRequestBody)
                .when()
                .post("http://localhost:8080/animals")
                .thenReturn();

        animalResponse = response.as(AnimalDto.class);

        // Assert - validate response: verify Animal fields.
        assertThat(animalResponse.getName(), equalTo("ThisIsMyLongName"));
        assertThat(animalResponse.getBreed(), equalTo("Mestizo"));
        assertThat(animalResponse.getGender(), equalTo("Female"));
        assertThat(animalResponse.isVaccinated(), equalTo(true));
        assertThat(animalResponse.getId(), notNullValue());

        // Assert - Verify db creation.
    }

    @AfterAll
    public void tearDown() {
        RestAssured.delete(String.format("http://localhost:8080/animals/%s", animalResponse.getId()));
    }
}

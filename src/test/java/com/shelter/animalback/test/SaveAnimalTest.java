package com.shelter.animalback.test;

import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SaveAnimalTest {
    @Test
    public void testSaveAnimalSuccessfully() {
        // Arrange - Instance animal with data for request body
        CreateAnimalBodyDto animalRequest = new CreateAnimalBodyDto();
        animalRequest.setName("Hela");
        animalRequest.setBreed("Mestizo");
        animalRequest.setGender("Female");
        animalRequest.setVaccinated(false);

        // Act - Make a POST request against the create animal endpoint
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(animalRequest)
                .when()
                .post("http://localhost:8080/animals")
                .thenReturn();

        AnimalDto animalResponse = response.as(AnimalDto.class);

        // Assert - validate response: verify Animal fields
        assertThat(animalResponse.getName(), equalTo("Hela"));
        assertThat(animalResponse.getBreed(), equalTo("Mestizo"));
        assertThat(animalResponse.getGender(), equalTo("Female"));
        assertThat(animalResponse.isVaccinated(), equalTo(false));
        assertThat(animalResponse.getId(), notNullValue());

        // Assert - Verify db creation.
    }
}

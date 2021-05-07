package com.shelter.animalback.component.api.animal;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SaveAnimalTest {
    public AnimalDto animalResponse;

    @Test
    @SneakyThrows
    public void createAnimalSuccessful() {
        var animal = new CreateAnimalBodyDto();
        animal.setName("ThisIsMyLongName");
        animal.setBreed("Mestizo");
        animal.setGender("Female");
        animal.setVaccinated(true);

        var createAnimalRequestBody = new ObjectMapper().writeValueAsString(animal);

        var response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createAnimalRequestBody)
                .when()
                .post("http://localhost:8080/animals")
                .thenReturn();

        animalResponse = response.as(AnimalDto.class);

        // Asserts Http Response
        assertThat(animalResponse.getName(), equalTo("ThisIsMyLongName"));
        assertThat(animalResponse.getBreed(), equalTo("Mestizo"));
        assertThat(animalResponse.getGender(), equalTo("Female"));
        assertThat(animalResponse.isVaccinated(), equalTo(true));
        assertThat(animalResponse.getId(), notNullValue());
    }

    @AfterAll
    public void tearDown() {
        RestAssured.delete(String.format("http://localhost:8080/animals/%s", animalResponse.getId()));
    }
}

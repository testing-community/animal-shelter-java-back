package com.shelter.animalback.component.api.animal;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SaveAnimalComponentTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    @SneakyThrows
    public void createAnimalSuccessful() {
        var animal = new CreateAnimalBodyDto();
        animal.setName("ThisIsMyLongName");
        animal.setBreed("Mestizo");
        animal.setGender("Female");
        animal.setVaccinated(true);

        var createAnimalRequestBody = new ObjectMapper().writeValueAsString(animal);

        var response = RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createAnimalRequestBody)
                .when()
                .post("/animals")
                .thenReturn();

        var animalResponse = response.as(AnimalDto.class);

        // Asserts Http Response
        assertThat(animalResponse.getName(), equalTo("ThisIsMyLongName"));
        assertThat(animalResponse.getBreed(), equalTo("Mestizo"));
        assertThat(animalResponse.getGender(), equalTo("Female"));
        assertThat(animalResponse.isVaccinated(), equalTo(true));
        assertThat(animalResponse.getId(), notNullValue());
    }
}

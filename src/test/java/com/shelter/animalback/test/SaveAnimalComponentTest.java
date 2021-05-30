package com.shelter.animalback.test;

import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SaveAnimalComponentTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void testSaveAnimalSuccessfully() {
        // Arrange - Instance animal with data for request body
        CreateAnimalBodyDto animalRequest = new CreateAnimalBodyDto();
        animalRequest.setName("Hela");
        animalRequest.setBreed("Mestizo");
        animalRequest.setGender("Female");
        animalRequest.setVaccinated(false);

        // Act - Make a POST request against the create animal endpoint
        MockMvcResponse response = RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(animalRequest)
                .when()
                .post("/animals")
                .thenReturn();

        AnimalDto animalResponse = response.as(AnimalDto.class);

        // Assert - validate response: verify Animal fields
        assertThat(animalResponse.getName(), equalTo("Hela"));
        assertThat(animalResponse.getBreed(), equalTo("Mestizo"));
        assertThat(animalResponse.getGender(), equalTo("Female"));
        assertThat(animalResponse.isVaccinated(), equalTo(false));
        assertThat(animalResponse.getId(), notNullValue());

        // Assert - Verify db creation.
        Optional<AnimalDao> animalQuery = animalRepository.findById(animalResponse.getId());
        assertThat(animalQuery.isPresent(), is(true));

        AnimalDao animalEntity = animalQuery.get();
        assertThat(animalEntity.getName(), equalTo("Hela"));
        assertThat(animalEntity.getBreed(), equalTo("Mestizo"));
        assertThat(animalEntity.getGender(), equalTo("Female"));
        assertThat(animalEntity.isVaccinated(), equalTo(false));
    }
}

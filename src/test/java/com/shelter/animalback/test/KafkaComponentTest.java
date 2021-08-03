package com.shelter.animalback.test;

import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import com.shelter.animalback.test.config.TestApplication;
import com.shelter.animalback.test.config.TestContextInitializer;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
@ContextConfiguration(initializers = TestContextInitializer.class)
public class KafkaComponentTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void testPublishSavedAnimal() {
        // Arrange - Instance animal with data for request body
        CreateAnimalBodyDto animalRequest = new CreateAnimalBodyDto();
        animalRequest.setName("Kira");
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

        // Assert - validate saved animal name is sent correctly to kafka topic
        verify(kafkaTemplate).send("test", "Kira");
    }

    @Test
    public void testTimeoutSendingSavedAnimal() {
        // Arrange - Instance animal with data for request body
        CreateAnimalBodyDto animalRequest = new CreateAnimalBodyDto();
        animalRequest.setName("Timeout");
        animalRequest.setBreed("Mestizo");
        animalRequest.setGender("Female");
        animalRequest.setVaccinated(false);

        when(kafkaTemplate.send("test", "Timeout")).thenThrow(new KafkaException("Interrupted"));

        // Act - Make a POST request against the create animal endpoint
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(animalRequest)
                .when()
                .post("/animals")
                .then()
                .status(HttpStatus.BAD_REQUEST)
                .body(is("Interrupted"));
    }
}

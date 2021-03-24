package com.shelter.animalback.component.api;

import com.shelter.animalback.controller.dto.AnimalDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class listAnimalTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        AnimalDto cat = new AnimalDto("Thor", "Birmano", "Male", false, new String[]{"Leucemia Felina"});
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(cat)
                .when()
                .post("/animals");
    }

    @Test
    public void listAnimalsSuccessfully() {
        RestAssured
                .when()
                .get("/animals")
                .then().assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void listAnimalsWithRightSchema() {
        RestAssured
                .when()
                .get("/animals")
                .then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("./animals.json"));
    }
}

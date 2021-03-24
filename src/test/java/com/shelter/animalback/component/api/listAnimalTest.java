package com.shelter.animalback.component.api;

import com.shelter.animalback.domain.Animal;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class listAnimalTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        Animal cat = new Animal("Bigotes", "Criollo", "Male", true, new String[]{"Rabia"});
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

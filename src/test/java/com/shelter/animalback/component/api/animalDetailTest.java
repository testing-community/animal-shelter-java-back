package com.shelter.animalback.component.api;

import com.shelter.animalback.domain.Animal;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class animalDetailTest {

    @LocalServerPort
    private int port;

    private Animal cat;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        cat = new Animal("Thor", "Birmano", "Male", false, new String[]{"Leucemia Felina"});
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(cat)
                .when()
                .post("/animals");
    }

    @Test
    public void animaDetailWithSuccessStatusCodeAndContentType() {
        RestAssured
                .when()
                .get("/animals/Thor")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void detailWithTheRightAnimal() {
        Animal animal =
                RestAssured
                        .when()
                        .get("/animals/Thor")
                        .then().extract().as(Animal.class);

        MatcherAssert.assertThat(animal.getName(), Matchers.equalTo(cat.getName()));
        MatcherAssert.assertThat(animal.getBreed(), Matchers.equalTo(cat.getBreed()));
        MatcherAssert.assertThat(animal.getGender(), Matchers.equalTo(cat.getGender()));
        MatcherAssert.assertThat(animal.isVaccinated(), Matchers.equalTo(cat.isVaccinated()));
    }
}

package com.shelter.animalback.component.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.domain.Animal;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class animalDetailTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private AnimalDto cat;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.port = port;

        cat = new AnimalDto("Thor", "Birmano", "Male", false, new String[]{"Leucemia Felina"});
        var catString = new ObjectMapper().writeValueAsString(cat);

        mockMvc.perform(post("/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(catString));
    }

    @Test
    public void animaDetailWithSuccessStatusCodeAndContentType() throws Exception {
        var response = mockMvc.perform(get("/animals/Thor")).andReturn().getResponse();

        MatcherAssert.assertThat(response.getStatus(), Matchers.equalTo(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    public void detailWithTheRightAnimal() throws Exception {
        var response = mockMvc.perform(get("/animals/Thor")).andReturn().getResponse();
        var animal = new ObjectMapper().readValue(response.getContentAsString(), Animal.class);

        MatcherAssert.assertThat(animal.getName(), Matchers.equalTo(cat.getName()));
        MatcherAssert.assertThat(animal.getBreed(), Matchers.equalTo(cat.getBreed()));
        MatcherAssert.assertThat(animal.getGender(), Matchers.equalTo(cat.getGender()));
        MatcherAssert.assertThat(animal.isVaccinated(), Matchers.equalTo(cat.isVaccinated()));
    }
}

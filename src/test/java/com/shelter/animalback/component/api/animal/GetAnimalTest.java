package com.shelter.animalback.component.api.animal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GetAnimalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() {
        var cat = new AnimalDao("Thor", "Birmano", "Male", false);
        animalRepository.save(cat);
    }

    @Test
    @SneakyThrows
    public void animaDetailWithSuccessStatusCodeAndContentType() {
        var response = mockMvc.perform(get("/animals/Thor")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    @SneakyThrows
    public void detailWithTheRightAnimal() {
        var response = mockMvc.perform(get("/animals/Thor")).andReturn().getResponse();
        var animal = new ObjectMapper().readValue(response.getContentAsString(), Animal.class);

        assertThat(animal.getName(), equalTo("Thor"));
        assertThat(animal.getBreed(), equalTo("Birmano"));
        assertThat(animal.getGender(), equalTo("Male"));
        assertThat(animal.isVaccinated(), equalTo(false));
    }
}

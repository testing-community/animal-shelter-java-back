package com.shelter.animalback.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.shelter.animalback.controller.AnimalController.map;

@Component
public class PetsListener {
    @Autowired private AnimalService animalService;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "pets")
    public void receive(String message) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var animalDto = mapper.readValue(message, CreateAnimalBodyDto.class);
        animalService.save(map(animalDto));
    }
}

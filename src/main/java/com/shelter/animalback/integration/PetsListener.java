package com.shelter.animalback.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.service.interfaces.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.shelter.animalback.controller.AnimalController.map;

@Component
@Slf4j
public class PetsListener {
    @Autowired private AnimalService animalService;

    @KafkaListener(topics = "${spring.kafka.topic.listen}", groupId = "pets")
    public void receive(@Payload String message) {
        try {
            log.info("Received pet: ".concat(message));
            var mapper = new ObjectMapper();
            var animal = mapper.readValue(message, CreateAnimalBodyDto.class);
            animalService.save(map(animal));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

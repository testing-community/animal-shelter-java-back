package com.shelter.animalback.controller;

import com.shelter.animalback.controller.dto.CreateAnimalBodyDto;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.controller.dto.UpdateAnimalBodyDto;
import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.exceptions.AnimalNotFoundException;
import com.shelter.animalback.exceptions.DataConflictException;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/animals")
    public ResponseEntity<Collection<AnimalDto>> listAnimals() {
        var animals = animalService.getAll();
        var dtos = animals.stream().map(animal -> map(animal)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/animals/{name}")
    public ResponseEntity<?> getAnimal(@PathVariable("name") String name) {
        try {
            var animal = animalService.get(name);

            return ResponseEntity.status(HttpStatus.OK).body(map(animal));
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("The animal called %s does not exists", name));
        }
    }

    @DeleteMapping("/animals/{name}")
    public ResponseEntity deleteAnimal(@PathVariable("name") String name) {
        try {
            animalService.delete(name);
            return ResponseEntity.noContent().build();
        } catch (AnimalNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("The animal called %s does not exists", name));
        }
    }

    @PostMapping("/animals")
    public ResponseEntity<?> saveAnimal(@RequestBody CreateAnimalBodyDto animalDto) {
        try {
            var animal = animalService.save(map(animalDto));
            return new ResponseEntity<AnimalDto>(map(animal), HttpStatus.CREATED);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("The animal called %s has already been created", animalDto.getName()));
        }
    }

    @PutMapping("/animals/{name}")
    public ResponseEntity updateAnimal(@PathVariable("name") String name, @RequestBody UpdateAnimalBodyDto animalDto) {
        try {
            var updatedAnimal = animalService.replace(name, map(animalDto));
            return new ResponseEntity<Animal>(updatedAnimal, HttpStatus.OK);
        } catch (AnimalNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The animal does not exist");
        } catch (DataConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The name can not be modified");
        }
    }

    private AnimalDto map(Animal animal) {
        return new AnimalDto(
                animal.getId(),
                animal.getName(),
                animal.getBreed(),
                animal.getGender(),
                animal.isVaccinated(),
                animal.getVaccines());
    }

    private Animal map(CreateAnimalBodyDto dto) {
        return new Animal(
                dto.getName(),
                dto.getBreed(),
                dto.getGender(),
                dto.isVaccinated(),
                dto.getVaccines());
    }

    private Animal map(UpdateAnimalBodyDto dto) {
        return new Animal(
                dto.getId(),
                dto.getName(),
                dto.getBreed(),
                dto.getGender(),
                dto.isVaccinated(),
                dto.getVaccines());
    }
}

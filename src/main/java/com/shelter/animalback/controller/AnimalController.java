package com.shelter.animalback.controller;

import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.exceptions.AnimalNotFoundException;
import com.shelter.animalback.exceptions.DataConflictException;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/animals")
    public Collection<Animal> listAnimals() {
        return animalService.getAll();
    }

    @GetMapping("/animals/{name}")
    public ResponseEntity getAnimal(@PathVariable("name") String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(animalService.get(name));
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
    public ResponseEntity saveAnimal(@RequestBody Animal animalDto) {
        try {
            var animal = animalService.save(animalDto);
            return new ResponseEntity<Animal>(animal, HttpStatus.CREATED);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("The animal called %s has already been created", animalDto.getName()));
        }
    }

    @PutMapping("/animals/{name}")
    public ResponseEntity updateAnimal(@PathVariable("name") String name, @RequestBody Animal animalDto) {
        try {
            var updatedAnimal = animalService.replace(name, animalDto);
            return new ResponseEntity<Animal>(updatedAnimal, HttpStatus.OK);
        } catch (AnimalNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The animal does not exist");
        } catch (DataConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The name can not be modified");
        }
    }
}

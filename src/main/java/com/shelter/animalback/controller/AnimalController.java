package com.shelter.animalback.controller;

import com.shelter.animalback.domain.Animal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
public class AnimalController {
    private HashMap<String, Animal> animalsList;

    public AnimalController() {
        animalsList = new HashMap<String, Animal>() {{
            put("bigotes", new Animal("Bigotes", "Bengalí", "Male", true, new String[]{"Rabia"}));
        }};
    }

    @GetMapping("/animals")
    public Collection<Animal> listAnimals() {
        return animalsList.values();
    }

    @GetMapping("/animal/{name}")
    public Animal getAnimal(@PathVariable("name") String name) {
        return animalsList.get(name.toLowerCase());
    }

    @DeleteMapping("/animal/{name}")
    public void deleteAnimal(@PathVariable("name") String name) {
        animalsList.remove(name.toLowerCase());
    }

    @PostMapping("/animal")
    public ResponseEntity saveAnimal(@RequestBody Animal animalDto) {
        String name = animalDto.getName();
        if (animalsList.containsKey(name.toLowerCase())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The animal has already been created");
        }

        animalsList.put(name.toLowerCase(), new Animal(animalDto.getName(), animalDto.getBreed(), animalDto.getGender(), animalDto.isVaccinated(), animalDto.getVaccines()));

        Animal returnedAnimal = animalsList.get(name.toLowerCase());
        return new ResponseEntity<Animal>(returnedAnimal, HttpStatus.CREATED);
    }

    @PutMapping("/animal/{name}")
    public ResponseEntity updateAnimal(@PathVariable("name") String name, @RequestBody Animal animalDto) {
        if (!animalsList.containsKey(name.toLowerCase())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The animal does not exist");
        }
        Animal animal = animalsList.get(name.toLowerCase());
        animal.setBreed(animalDto.getBreed());
        animal.setVaccinated(animalDto.isVaccinated());
        animal.setGender(animalDto.getGender());
        animal.setVaccines(animalDto.getVaccines());
        animalsList.replace(name.toLowerCase(), animal);

        Animal returnedAnimal = animalsList.get(name.toLowerCase());
        return new ResponseEntity<Animal>(returnedAnimal, HttpStatus.OK);
    }
}

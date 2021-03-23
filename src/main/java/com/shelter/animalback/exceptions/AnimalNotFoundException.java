package com.shelter.animalback.exceptions;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String name) {
        super(String.format("the %s animal does not exists", name));
    }
}

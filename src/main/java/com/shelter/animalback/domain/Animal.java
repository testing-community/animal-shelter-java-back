package com.shelter.animalback.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Animal {
    private String name;
    private String breed;
    private String gender;
    private boolean isVaccinated;
    private String[] vaccines;
    private long id;

    public Animal(long id, String name, String breed, String gender, boolean isVaccinated, String[] vaccines) {
        this(name, breed, gender, isVaccinated, vaccines);
        this.id = id;
    }

    public Animal(String name, String breed, String gender, boolean isVaccinated, String[] vaccines) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.isVaccinated = isVaccinated;
        this.vaccines = vaccines;
    }
}

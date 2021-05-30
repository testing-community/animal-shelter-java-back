package com.shelter.animalback.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnimalDto {
    private long id;
    private String name;
    private String breed;
    private String gender;
    private boolean isVaccinated;
    private String[] vaccines;
    private int lifeExpectancy;

    public AnimalDto(long id, String name, String breed, String gender, boolean isVaccinated, String[] vaccines) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.isVaccinated = isVaccinated;
        this.vaccines = vaccines;
    }
}

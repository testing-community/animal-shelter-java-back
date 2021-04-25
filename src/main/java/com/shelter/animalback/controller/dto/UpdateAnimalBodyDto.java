package com.shelter.animalback.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAnimalBodyDto extends CreateAnimalBodyDto{
    private long id;

    public UpdateAnimalBodyDto(long id, String name, String breed, String gender, boolean isVaccinated, String[] vaccines) {
        super(name, breed, gender, isVaccinated, vaccines);
        this.id = id;
    }
}

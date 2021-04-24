package com.shelter.animalback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ANIMAL")
public class AnimalDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANIMAL_GENERATOR")
    @SequenceGenerator(name = "ANIMAL_GENERATOR", sequenceName = "ANIMAL_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true, length = 10)
    private String name;

    private String breed;
    private String gender;
    private boolean vaccinated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "VACCINES_BY_ANIMAL",
            joinColumns = @JoinColumn(name = "animal_id"),
            foreignKey = @ForeignKey(name="fk_animal"),
            inverseJoinColumns = @JoinColumn(name = "vaccine_id"),
            inverseForeignKey = @ForeignKey(name="fk_vaccine"))
    private List<VaccineDao> vaccines;

    public AnimalDao(String name, String breed, String gender, boolean vaccinated) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.vaccinated = vaccinated;
    }
}

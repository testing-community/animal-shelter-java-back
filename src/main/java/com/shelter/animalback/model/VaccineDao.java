package com.shelter.animalback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VACCINE")
@SequenceGenerator(name="VACCINE_SEQ", initialValue = 1)
public class VaccineDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VACCINE_SEQ")
    private Long id;

    @Column(unique = true)
    private String name;

    public VaccineDao(String name) {
        this.name = name;
    }
}

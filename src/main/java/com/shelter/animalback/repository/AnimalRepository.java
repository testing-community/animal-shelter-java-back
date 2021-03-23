package com.shelter.animalback.repository;

import com.shelter.animalback.model.AnimalDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<AnimalDao, Long> {
    AnimalDao findByName(String name);
}

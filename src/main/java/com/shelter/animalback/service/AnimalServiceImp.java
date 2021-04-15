package com.shelter.animalback.service;

import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.exceptions.AnimalNotFoundException;
import com.shelter.animalback.exceptions.DataConflictException;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AnimalServiceImp implements AnimalService {

    @Autowired
    private AnimalRepository repository;

    @Override
    public List<Animal> getAll() {
        var animalsDao = repository.findAll();

        return StreamSupport
                .stream(animalsDao.spliterator(), false)
                .map(dao -> this.map(dao)).collect(Collectors.toList());
    }

    @Override
    public Animal get(String name) {
        var animalDao = repository.findByName(name);

        if (animalDao == null) {
            throw new AnimalNotFoundException(name);
        }

        return map(animalDao);
    }

    @Override
    public Animal save(Animal animal) {
        var dao = map(animal);

        var saved = repository.save(dao);

        return map(saved);
    }

    @Override
    public Animal replace(String name, Animal animal) {
        if (animal.getName() != null && !name.equals(animal.getName())) {
            throw new DataConflictException();
        }

        var oldAnimal = repository.findByName(name);

        if (oldAnimal == null) {
            throw new AnimalNotFoundException(name);
        }

        if (animal.getName() == null) {
            animal.setName(name);
        }

        var newDao = map(animal);
        newDao.setId(oldAnimal.getId());

        repository.save(newDao);

        return animal;
    }

    @Override
    public void delete(String name) {
        var dao = repository.findByName(name);

        if (dao == null) {
            throw new AnimalNotFoundException(name);
        }

        repository.delete(dao);
    }

    private Animal map(AnimalDao dao) {
        var vaccinesDao = dao.getVaccines();

        var vaccines = vaccinesDao == null ? new String[0] :
                vaccinesDao.stream().map(vaccineDao -> vaccineDao.getName()).toArray(size -> new String[size]);

        return new Animal(
                dao.getId(),
                dao.getName(),
                dao.getBreed(),
                dao.getGender(),
                dao.isVaccinated(),
                vaccines
        );
    }

    private AnimalDao map(Animal animal) {
        return new AnimalDao(
                animal.getName(),
                animal.getBreed(),
                animal.getGender(),
                animal.isVaccinated());
    }
}

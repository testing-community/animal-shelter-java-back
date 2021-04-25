DROP TABLE IF EXISTS vaccines_by_animal;
DROP TABLE IF EXISTS animal;
DROP TABLE IF EXISTS vaccine;

CREATE SEQUENCE IF NOT EXISTS ANIMAL_SEQ START 2;
CREATE SEQUENCE IF NOT EXISTS VACCINE_SEQ START 1;

CREATE TABLE IF NOT EXISTS animal
(
    id bigint PRIMARY KEY,
    breed VARCHAR(255),
    gender VARCHAR(255),
    name VARCHAR(10),
    vaccinated boolean NOT NULL,
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS vaccine
(
    id bigint PRIMARY KEY,
    name VARCHAR(255),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS vaccines_by_animal
(
    animal_id bigint NOT NULL,
    vaccine_id bigint NOT NULL,
    CONSTRAINT fk_animal FOREIGN KEY (animal_id)
        REFERENCES animal (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_vaccine FOREIGN KEY (vaccine_id)
        REFERENCES public.vaccine (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
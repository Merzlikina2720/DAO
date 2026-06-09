package com.example.demo.repository;

import com.example.demo.entity.Person;
import com.example.demo.entity.Person.PersonId; // корректный импорт
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, PersonId> {

    List<Person> findByCityOfLiving(String city);
    List<Person> findByIdAgeLessThanOrderByIdAgeAsc(Integer age);
    Optional<Person> findByIdNameAndIdSurname(String name, String surname);
}
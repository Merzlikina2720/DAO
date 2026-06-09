package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.entity.Person.PersonId; // корректный импорт вложенного класса
import com.example.demo.repository.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{name}/{surname}/{age}")
    public ResponseEntity<Person> getPersonById(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable Integer age) {
        PersonId id = new Person.PersonId(age, name, surname); // корректное создание вложенного класса
        Optional<Person> person = personRepository.findById(id);
        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        URI location = URI.create("/persons/" + person.getId().getAge() +
                "/" + person.getId().getName() + "/" + person.getId().getSurname());
        return ResponseEntity.created(location).body(savedPerson);
    }

    @PutMapping("/{name}/{surname}/{age}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable Integer age,
            @RequestBody Person personDetails) {
        PersonId id = new Person.PersonId(age, name, surname);
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personDetails.setId(id);
        Person updatedPerson = personRepository.save(personDetails);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{name}/{surname}/{age}")
    public ResponseEntity<Void> deletePerson(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable Integer age) {
        PersonId id = new Person.PersonId(age, name, surname);
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<Person>> getPersonsByCity(@RequestParam String city) {
        List<Person> persons = personRepository.findByCityOfLiving(city);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/younger-than")
    public ResponseEntity<List<Person>> getPersonsYoungerThan(@RequestParam Integer age) {
        List<Person> persons = personRepository.findByIdAgeLessThanOrderByIdAgeAsc(age);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/by-name-surname")
    public ResponseEntity<Person> getPersonByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname) {
        Optional<Person> person = personRepository.findByIdNameAndIdSurname(name, surname);
        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
package com.example.demo.repository;

import com.example.demo.entity.Person;
import com.example.demo.entity.PersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, PersonId> {
    List<Person> findByCityOfLiving(String city);
}

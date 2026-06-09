package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "PERSONS")
public class Person implements Serializable {

    @EmbeddedId
    private PersonId id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city_of_living")
    private String cityOfLiving;

    // Конструкторы
    public Person() {}

    public Person(Integer age, String name, String surname, String phoneNumber, String cityOfLiving) {
        this.id = new PersonId(age, name, surname);
        this.phoneNumber = phoneNumber;
        this.cityOfLiving = cityOfLiving;
    }

    // Вложенный класс для составного ключа
    @Embeddable
    public static class PersonId implements Serializable {
        @Column(name = "age")
        private Integer age;

        @Column(name = "name")
        private String name;

        @Column(name = "surname")
        private String surname;

        // Конструкторы
        public PersonId() {}

        public PersonId(Integer age, String name, String surname) {
            this.age = age;
            this.name = name;
            this.surname = surname;
        }

        // Геттеры
        public Integer getAge() { return age; }
        public String getName() { return name; }
        public String getSurname() { return surname; }

        // Сеттеры
        public void setAge(Integer age) { this.age = age; }
        public void setName(String name) { this.name = name; }
        public void setSurname(String surname) { this.surname = surname; }

        // Обязательные методы для Serializable ключей
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PersonId)) return false;
            PersonId personId = (PersonId) o;
            return Objects.equals(age, personId.age) &&
                    Objects.equals(name, personId.name) &&
                    Objects.equals(surname, personId.surname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, name, surname);
        }
    }

    // Геттеры для полей через составной ключ
    public Integer getAge() {
        return id.getAge();
    }

    public String getName() {
        return id.getName();
    }

    public String getSurname() {
        return id.getSurname();
    }

    // Сеттеры для полей через составной ключ
    public void setAge(Integer age) {
        id.setAge(age);
    }

    public void setName(String name) {
        id.setName(name);
    }

    public void setSurname(String surname) {
        id.setSurname(surname);
    }

    // Геттеры и сеттеры для остальных полей
    public PersonId getId() {
        return id;
    }

    public void setId(PersonId id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCityOfLiving() {
        return cityOfLiving;
    }

    public void setCityOfLiving(String cityOfLiving) {
        this.cityOfLiving = cityOfLiving;
    }
}
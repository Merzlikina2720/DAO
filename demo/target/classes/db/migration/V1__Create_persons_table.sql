CREATE TABLE PERSONS (
    age INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    city_of_living VARCHAR(255),
    PRIMARY KEY (age, name, surname)
);
package com.example.springbootdemo.dao;

import com.example.springbootdemo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDAO {
    int insertPerson(UUID id, Person person);
    default int insertPerson(Person person){
       UUID id = UUID.randomUUID();
       return insertPerson(id,person);
    }
    List<Person> selectAllPeople();
    Optional<Person> getPersonById(UUID id);
    int deletePersonById(UUID id);
    int updatePerson(UUID id,Person person);
}

package com.example.springbootdemo.dao;

import com.example.springbootdemo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDAO {
    private static List<Person> db = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        db.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return db.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Person obj = getPersonById(id).orElse(null);
        if (obj != null) {
            db.remove(obj);
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePerson(UUID id, Person person) {
        Person obj = getPersonById(id).orElse(null);
        if (obj != null) {
            db = db.stream().map(person1 -> new Person(id, person.getName())).collect(Collectors.toList());
            return 1;
        }
        return 0;
    }
}

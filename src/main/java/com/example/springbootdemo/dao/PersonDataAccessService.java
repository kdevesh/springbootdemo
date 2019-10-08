package com.example.springbootdemo.dao;

import com.example.springbootdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        int insert = jdbcTemplate.update("insert into person (id,name) values (?,?)", id, person.getName());
        return insert;
    }


    @Override
    public List<Person> selectAllPeople() {
        return jdbcTemplate.query("select id,name from person",
                (resultSet, i) -> {
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(id, name);
                }
        );
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
//        return Optional.of(jdbcTemplate.query("select id,name from person where id='" + id+"'", (resultSet, i) -> {
//            UUID uuid = UUID.fromString(resultSet.getString("id"));
//            String name = resultSet.getString("name");
//            return new Person(uuid, name);
//        }).get(0));
        Person person = jdbcTemplate.queryForObject("select id,name from person where id=?",
                new Object[]{id},
                (resultSet, i) -> {
                    UUID uuid = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(uuid, name);
                });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        int delete = jdbcTemplate.update("delete from person where id=?", id);
        return delete;
    }

    @Override
    public int updatePerson(UUID id, Person person) {
        int update = jdbcTemplate.update("update person set name=? where id=?", person.getName(), id);
        return update;
    }
}

package com.github.hib.dao;

import com.github.hib.entity.Role;
import com.github.model.Person;

import java.util.List;

public interface PersonDao {


    int createPerson(Person person);

    Person getByLogin(String login);
    Person getById(Integer id);
    Person getByRole(Role role);

    void updatePerson(int userId, String pass);
    void deletePerson(Integer id);
    List<Person> getPersons();
    List<Person> getAll();
}

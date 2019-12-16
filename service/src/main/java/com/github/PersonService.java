package com.github;

import com.github.hib.entity.Role;
import com.github.model.Person;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PersonService {
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    int createPerson(Person person);

    Person getByLogin(String login);
    Person getById(Integer id);
    Person getByRole(Role role);

    void updatePerson(int userId, String pass);
    void deletePerson(Integer id);
    List<Person> getAll();
    public Person userName (String login, String password);
}

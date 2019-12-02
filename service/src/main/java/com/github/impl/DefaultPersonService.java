package com.github.impl;

import com.github.PersonService;
import com.github.hib.dao.PersonDao;
import com.github.hib.entity.Role;
import com.github.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultPersonService implements PersonService {

    private PersonDao personDao;

    public DefaultPersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    @Transactional
    public int createPerson(Person person) {
        return personDao.createPerson(person);
    }

    @Override
    @Transactional
    public Person getByLogin(String login) {
        return personDao.getByLogin(login);
    }

    @Override
    @Transactional
    public Person getByRole(Role role) {
        return personDao.getByRole(role);
    }

    @Override
    @Transactional
    public void updatePerson(String login, String pass) {
        personDao.updatePerson(login,pass);
    }

    @Override
    @Transactional
    public void deletePerson(Integer id) {
        personDao.deletePerson(id);

    }

    @Override
    @Transactional
    public List<Person> getAll() {
        return personDao.getAll();
    }
}

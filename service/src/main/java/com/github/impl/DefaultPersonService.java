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
    public Person getById(Integer id) {
        return personDao.getById(id);
    }

    @Override
    @Transactional
    public Person getByRole(Role role) {
        return personDao.getByRole(role);
    }

    @Override
    @Transactional
    public void updatePerson(int userId, String pass) {
        personDao.updatePerson(userId,pass);
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


    @Override
    @Transactional
    public Person userName (String login, String password){
        Person user = personDao.getByLogin(login);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

}

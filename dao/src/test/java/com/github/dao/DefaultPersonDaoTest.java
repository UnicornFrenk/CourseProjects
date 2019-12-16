package com.github.dao;

import com.github.config.DaoConfig;
import com.github.hib.dao.PersonDao;
import com.github.hib.dao.impl.DefaultPersonDao;
import com.github.hib.entity.Role;
import com.github.model.Person;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultPersonDaoTest {


    @Autowired
    private PersonDao personDao;

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void create() {
        Person test = new Person("Lola","Lola", Role.USER);
        personDao.createPerson(test);
        String login = test.getLogin();
        String exp = "Lola";
        assertEquals( exp,login);

    }

    @Test
    public void getByLogin() {
        Person person = new Person("Sofia","Sofia", Role.USER);
        personDao.createPerson(person);
        Person test =  personDao.getByLogin("Sofia");
        String password = test.getPassword();
        String expPassword = "Sofia";
        assertEquals(expPassword,password);

    }


    @Test
    public void getById() {
        Person person = new Person("Sofia","Sofia", Role.USER);
        personDao.createPerson(person);
        Person test =  personDao.getById(person.getId());
        Integer actId = test.getId();
        assertNotNull(actId);

    }

    @Test
    public void getAll() {
        List<Person> expected = personDao.getAll();
        assertNotNull(expected);
    }

    @Test
    public void getRole() {
        Person person = new Person("Sofia","Sofia", Role.USER);
        personDao.createPerson(person);
        Role role = personDao.getByLogin("Sofia").getRole();
        Role exp = Role.USER;
        assertEquals( exp,role);

    }
}
package com.github.impl;


import com.github.hib.dao.PersonDao;
import com.github.hib.entity.Role;
import com.github.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static com.github.hib.entity.Role.ROLE_USER;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)
public class DefaultUserServiceTest {

    @Mock
    PersonDao dao;

    @InjectMocks
    DefaultPersonService service;

    @InjectMocks
    DefaultPersonService personService;

    @Test
    public void testLoginNotExist() {
        when(dao.getByLogin("ADMIN")).thenReturn(null);

        Person login = service.userName("ADMIN", "ADMIN");

        assertNull(login);
    }

    @Test
    public void testLoginCorrect() {
        when(dao.getByLogin("admin")).thenReturn(new Person(null, "admin", "pass", null));

        Person userFromDb = service.userName("admin", "pass");

        assertNotNull(userFromDb);
        assertEquals(userFromDb.getLogin(), "admin");
        assertNotNull(userFromDb.getPassword(), "pass");
    }

    @Test
    public void testLoginWrongPass() {
        when(dao.getByLogin("admin")).thenReturn(new Person(null, "admin", "pass", null));

        Person login = personService.getByLogin("admin");

        assertNotNull(login);
    }

    @Test
    public void getAllTest() {
        when(dao.getAll()).thenReturn(new ArrayList<Person>());
        List<Person> personList = personService.getAll();
        assertNotNull(personList);
    }

    @Test
    public void getAllPersonsTest() {
        when(dao.getPersons()).thenReturn(new ArrayList<Person>());
        List<Person> personList = dao.getPersons();
        assertNotNull(personList);
    }

    @Test
    public void getRoleTest() {

        Person person = new Person("null", "null", ROLE_USER);
        when(dao.getByLogin("null")).thenReturn(person);

        Role personFromDb = dao.getByLogin("null").getRole();
        Role expRole = ROLE_USER;
        assertNotNull(personFromDb);
        assertEquals(expRole, personFromDb);
    }

    @Test
    public void updateUserTest() {
        doNothing().when(dao).updatePerson(anyInt(), any());

        personService.updatePerson(1, "4321");

        verify(dao).updatePerson(1, "4321");
    }

    @Test
    public void deleteUserTest() {
        doNothing().when(dao).deletePerson(anyInt());
        personService.deletePerson(1);
        verify(dao).deletePerson(1);

    }

    @Test
    public void getByRoleTest() {
        Person person = new Person("null", "null", ROLE_USER);
        when(dao.getByRole(ROLE_USER)).thenReturn(person);

        Role personFromDb = personService.getByRole(ROLE_USER).getRole();

        assertNotNull(personFromDb);
        assertEquals(ROLE_USER, personFromDb);
    }

    @Test
    public void getByLoginRoTest() {
        Person person = new Person("null", "null", ROLE_USER);
        when(dao.getByLogin("null")).thenReturn(person);

        Person personByLogin = personService.getByLogin("null");

        assertNotNull(personByLogin);
    }

}

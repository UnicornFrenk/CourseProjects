package com.github.hib.dao.impl;

import com.github.hib.dao.converters.PersonConverter;
import com.github.hib.dao.PersonDao;
import com.github.hib.entity.PersonEntity;
import com.github.hib.entity.Role;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPersonDao implements PersonDao {
    private static final Logger log = LoggerFactory.getLogger(
            DefaultPersonDao.class);
    private final SessionFactory sessionFactory;

    public DefaultPersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public int createPerson(Person person) {
        PersonEntity personEntity = PersonConverter.toEntity(
                new Person(person.getId(), person.getLogin(),
                           person.getPassword(), person.getRole()));
        Session session = sessionFactory.getCurrentSession();
        session.save(personEntity);
        return personEntity.getId();
    }

    @Override
    @Transactional
    public Person getByLogin(String login) {

        Session currentSession = sessionFactory.getCurrentSession();
        PersonEntity personEntity = (PersonEntity) currentSession
                                                                 .createQuery(
                                                          "from PersonEntity p where p.login=:login")
                                                                 .setParameter("login", login)
                                                                 .uniqueResult();
        return PersonConverter.fromEntity(personEntity);
    }

    @Override
    @Transactional
    public Person getByRole(Role role) {
        PersonEntity personEntity;
        try {
            personEntity = (PersonEntity) EntityManagerUtil.getEntityManager()
                                                           .createQuery(
                                                                   "from PersonEntity p where p.role = :role")
                                                           .setParameter("role",
                                                                         role)
                                                           .getSingleResult();
        } catch (NoResultException e) {
            log.info("user not found by login{}", role);
            personEntity = null;
        }
        return PersonConverter.fromEntity(personEntity);
    }

    @Override
    @Transactional
    public void updatePerson(String login, String pass) {
        pass = "www";
        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "update PersonEntity p set p.password = :pass where p.login = :login")
               .setParameter("login", login)
               .setParameter("pass", pass)
               .executeUpdate();
    }

    @Override
    @Transactional
    public void deletePerson(Integer id) {

        final Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from PersonEntity p where p.id = :id")
               .setParameter("id", id)
               .executeUpdate();
    }

    @Override
    public List<Person> getAll() {
        final List<PersonEntity> personList = sessionFactory.getCurrentSession()
                                                            .createQuery(
                                                                    "from PersonEntity ")
                                                            .list();
        return personList.stream()
                         .map(PersonConverter::fromEntity)
                         .collect(Collectors.toList());
    }

    public List<Person> getPersons() {
        final List<Person> personList = sessionFactory.getCurrentSession()
                                                      .createQuery(
                                                              "from " + "PersonEntity ")
                                                      .list();
        return personList;
    }
}

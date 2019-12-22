package com.github.criteria;

import com.github.hib.entity.PersonDetails;
import com.github.hib.entity.PersonEntity;
import com.github.hib.entity.Role;
import com.github.hib.util.EntityManagerUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

        @BeforeAll
        static public void init() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            em.getTransaction().begin();
            PersonDetails personDetails = new PersonDetails(null,null,null,null,null, null);
            PersonEntity person = new PersonEntity(null, "user1", "user1",
                                                   Role.ROLE_USER, personDetails);
            personDetails.setPerson(person);

            em.persist(person);

            em.persist(new PersonEntity(null, "user2", "user2",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user3", "user3",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user4", "user4",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user5", "user5",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "ADMIN", "ADMIN", Role.ROLE_ADMIN, null));
            em.persist(new PersonEntity(null, "user6", "user6", Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user7", "user7",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user8", "user8",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user9", "user9",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "user10", "user10",Role.ROLE_USER, null));
            em.persist(new PersonEntity(null, "ADMIN2", "ADMIN2", Role.ROLE_ADMIN, null));

            em.getTransaction().commit();
            em.clear();
            em.close();
        }

        @Test
        public void getAllPersons() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);
            criteria.select(criteria.from(PersonEntity.class));
            List<PersonEntity> personList = em.createQuery(criteria).getResultList();
            personList.forEach(System.out::println);

            assertNotNull(personList);
        }

        @Test
        public void getPersonByLoginTest() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);
            Root<PersonEntity> personRoot = criteria.from(PersonEntity.class);
            criteria.select(personRoot)
                    .where(cb.equal(personRoot.get("login"), "user8"));
            List<PersonEntity> personList = em.createQuery(criteria).getResultList();
            personList.forEach(System.out::println);

            assertNotNull(personList);
        }

    @Test
    public void getPersonByRoleTest() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);
        Root<PersonEntity> personRoot = criteria.from(PersonEntity.class);
        criteria.select(personRoot)
                .where(cb.equal(personRoot.get("role"), Role.ROLE_USER));
        List<PersonEntity> personList = em.createQuery(criteria).getResultList();
        personList.forEach(System.out::println);

        assertNotNull(personList);
    }

        @Test
        public void likeTest() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);
            Root<PersonEntity> emp = criteria.from(PersonEntity.class);
            criteria.select(emp)
                    .where(cb.like(emp.get("login"), "%us%"));
            List<PersonEntity> personList = em.createQuery(criteria).getResultList();
            personList.forEach(System.out::println);

            assertNotNull(personList);
        }

        @Test
        public void isNullTest() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);
            Root<PersonEntity> personRoot = criteria.from(PersonEntity.class);
            criteria.select(personRoot).where(cb.isNotNull(personRoot.get("login")));
            List<PersonEntity> personList = em.createQuery(criteria).getResultList();
            personList.forEach(System.out::println);

            assertNotNull(personList);
        }


        @Test
        public void pagingTest() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PersonEntity> personCriteriaQuery = cb.createQuery(PersonEntity.class);
            personCriteriaQuery.select(personCriteriaQuery.from(PersonEntity.class));

            TypedQuery<PersonEntity> typedQuery = em.createQuery(personCriteriaQuery);
            typedQuery.setFirstResult(2);
            typedQuery.setMaxResults(2);
            List<PersonEntity> employees = typedQuery.getResultList();

            employees.forEach(System.out::println);
        }

        @Test
        public void countTest() {
            EntityManager em = EntityManagerUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
            criteria.select(cb.count(criteria.from(PersonEntity.class)));
            long count = em.createQuery(criteria).getSingleResult();
            System.out.println(count);

            assertNotNull(count);
        }


        @AfterAll
        public static void cleanUp() {
            //HibernateUtil.closeEMFactory();
        }
    }


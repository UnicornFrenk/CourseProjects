package com.github.hql;

import com.github.hib.entity.*;
import com.github.hib.util.EntityManagerUtil;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HQLTests {
        @BeforeAll
        static public void init() {

            EntityManager em = EntityManagerUtil.getEntityManager();
            em.getTransaction().begin();

            CategoryEntity category1 = new CategoryEntity(null, "fruits", new ArrayList<>());
            category1.getItems().add(new ItemEntity("lemon", "lemon, Canada", 720, 200));
            category1.getItems().add(new ItemEntity("lemon", "lemon, USA", 800, 300));
            category1.getItems().add(new ItemEntity("lemon", "lemon, Africa", 1000, 400));

            CategoryEntity category2 = new CategoryEntity(null, "sweets", new ArrayList<>());
            category2.getItems().add(new ItemEntity("candy", "chocolate candy", 720, 200));
            category2.getItems().add(new ItemEntity("candy", "lollipop", 800, 300));
            category2.getItems().add(new ItemEntity("candy", "chewing candy", 1000, 400));

            em.persist(category1);
            em.persist(category2);


//            PersonEntity person1 = new PersonEntity(null, "user1", "user1", Role.USER, new PersonDetails());
//            PersonDetails personDetails1 = new PersonDetails(person1.getId(),"Minsk","Minsk","Minsk","Minsk",person1);
//            person1.setPersonDetails(personDetails1);
//            em.persist(person1);
            em.persist(new PersonEntity(null, "user1", "user1",Role.USER, new PersonDetails()));
            em.persist(new PersonEntity(null, "user2", "user2",Role.USER, new PersonDetails()));
            em.persist(new PersonEntity(null, "user3", "user3",Role.USER, new PersonDetails()));
            em.persist(new PersonEntity(null, "user4", "user4",Role.USER, new PersonDetails()));
            em.persist(new PersonEntity(null, "user5", "user5",Role.USER, new PersonDetails()));

            em.getTransaction().commit();
            em.clear();
            em.close();
        }

    @Test
    public void hql() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("from ItemEntity ");
        // timeout - в milliseconds
        query.setTimeout(1000)
                // включить в кеш запросов
                .setCacheable(true)
                // добавлять в кэш, но не считывать из него
                .setCacheMode(CacheMode.REFRESH)
                .setHibernateFlushMode(FlushMode.COMMIT);
                // сущности и коллекции помечаюся как только для чтения
                //.setReadOnly(true);

        System.out.println(query.list());
    }


    @Test
    public void selectAll() {
        Session session = EntityManagerUtil.getEntityManager();

        Query query = session.createQuery("from ItemEntity as i");
        query.list().forEach(System.out::println);
    }

    @Test
    public void selectAllLogin() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("select p.login from PersonEntity as p");
        final List<String> list = query.list();
        list.forEach(System.out::println);
    }

    @Test
    public void selectTestClauseObject() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("select i.name from ItemEntity as i");
        query.list().forEach(System.out::println);
    }

    @Test
    public void selectTestClauseObjectWhere() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("select i.name from ItemEntity as i where i.name = 'candy'");
        query.list().forEach(System.out::println);
    }

    @Test
    public void orderByTest() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("from ItemEntity as i order by i.id desc");
        query.list().forEach(System.out::println);
    }

//    @Test
//    public void groupByTest() {
//        Session session = EntityManagerUtil.getSession();
//        Query query = session.createQuery("select count(i.name), i.category from ItemEntity as i group by i.name");
//        query.list().forEach( -> {
//            Object[] person = (Object[]) i;
//            System.out.println("Имя: " + person[1] + " количество:" + person[0]);
//        });
//    }

    @Test
    public void parameterTest() {
        Session session = EntityManagerUtil.getEntityManager();
        String name = "lemon";
       // Query badQuery = session.createQuery("from Employee e where e.name = " + name);
        Query query = session.createQuery("from ItemEntity i where i.name = :name");
        query.setParameter("name", name)
                .getResultList().forEach(System.out::println);
    }

    @Test
    public void parameterOrderTest() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery(
                "from PersonEntity p where p.login= ?0 and p.id > :id");
        query.setParameter(0, "User3")
                .setParameter("id", 3)
                .getResultList().forEach(System.out::println);
    }

    @Test
    public void parameterListTest() {
        Session session = EntityManagerUtil.getEntityManager();
        final List<Integer> values = Arrays.asList(1, 4);
        Query query = session.createQuery("from ItemEntity i where i.id in (:ids)");
        query.setParameter("ids", values)
                .getResultList().forEach(System.out::println);
    }

//    @Test
//    public void countDistinctTest() {
//        Session session = EntityManagerUtil.getSession();
//        Query query = session.createQuery("select count(distinct p.login), p.login from PersonEntity) p group by p.login");
//        query.getResultList().forEach(employees -> {
//            Object[] emp = (Object[]) employees;
//            System.out.println("Имя: " + emp[1] + " количество:" + emp[0]);
//        });
//    }

    @Test
    public void like() {
        Session session = EntityManagerUtil.getEntityManager();
        String pattern = "lem";
        Query query = session.createQuery("from ItemEntity i where i.name like :name order by i.name");
        query.setParameter("name", pattern +"%");
        System.out.println(query.list());
    }

    @Test
    public void updateEmployee() {
        Session session = EntityManagerUtil.getEntityManager();
        session.beginTransaction();
        session.createQuery("update PersonEntity p set p.password = :pass where login = :name")
                .setParameter("name", "User4")
                .setParameter("pass", "123")
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void deleteTest() {
        PersonEntity person = new PersonEntity(null, "Tuk","Tuk",Role.USER,null);
        Session session = EntityManagerUtil.getEntityManager();
        session.getTransaction().begin();
        session.persist(person);
        session.createQuery("delete from PersonEntity p where p.id = :id")
                .setParameter("id", person.getId())
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void aggFun() {
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("select max(p.id) from PersonEntity p");
        System.out.println(query.list());
    }

    @Test
    public void leftJoinTest() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        List<ItemEntity> items = em.createQuery(
                "select distinct i " +
                        "from ItemEntity i " +
                        "left outer join i.category c " +
                        "where c.nameCategory = 'sweets'", ItemEntity.class)
                .getResultList();
//        System.out.println("tututu");
//        authors.forEach(author -> System.out.println(author.getName()));
        items.forEach(System.out::println);
    }

    @Test
    public void withJoinTest() {
        Session session = EntityManagerUtil.getEntityManager();
        List<ItemEntity> items = session.createQuery(
                "select distinct i " +
                        "from ItemEntity i " +
                        "inner join i.category c on c.nameCategory = 'sweets'")
                .getResultList();
        items.forEach(System.out::println);
    }

    @Test
    public void paginationTest() {
        final List<ItemEntity> page0 = getPage(0);
        System.out.println("page 0");
        System.out.println(page0);

        final List<ItemEntity> page1 = getPage(1);
        System.out.println("page 1");
        System.out.println(page1);

        final List<ItemEntity> page2 = getPage(2);
        System.out.println("page 2");
        System.out.println(page2);
    }

    private List<ItemEntity> getPage(int page) {
        int pageSize = 2;
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("from ItemEntity i");
        return query.setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

}

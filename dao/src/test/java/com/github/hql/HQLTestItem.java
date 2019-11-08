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

public class HQLTestItem {

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
            em.getTransaction().commit();
            em.clear();
            em.close();
        }

        @Test
        public void selectAllItemTest() {
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
        public void selectAllItemTestAlias() {
            Session session = EntityManagerUtil.getEntityManager();

            Query query = session.createQuery("from ItemEntity as i");
            query.list().forEach(System.out::println);
        }


        @Test
        public void selectAllItemNameTest() {
            Session session = EntityManagerUtil.getEntityManager();
            Query query = session.createQuery("select i.name from ItemEntity as i");
            query.list().forEach(System.out::println);
        }

        @Test
        public void selectItemByNameTest() {
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
        public void parameterListTest() {
            Session session = EntityManagerUtil.getEntityManager();
            final List<Integer> values = Arrays.asList(1, 4);
            Query query = session.createQuery("from ItemEntity i where i.id in (:ids)");
            query.setParameter("ids", values)
                    .getResultList().forEach(System.out::println);
        }

        @Test
        public void like() {
            Session session = EntityManagerUtil.getEntityManager();
            String pattern = "lemo";
            Query query = session.createQuery("from ItemEntity i where i.name like :name order by i.name");
            query.setParameter("name", pattern +"%");
            System.out.println(query.list());
        }

    @Test
    public void updateItem() {
        Session session = EntityManagerUtil.getEntityManager();
        session.beginTransaction();
        session.createQuery("update ItemEntity i set i.price = :price where name = :name")
                .setParameter("name", "apple")
                .setParameter("price", 21)
                .executeUpdate();
        session.getTransaction().commit();
    }
        @Test
        public void deleteItemTest() {
            ItemEntity item = new ItemEntity("look","look",300,300);
            Session session = EntityManagerUtil.getEntityManager();
            session.getTransaction().begin();
            session.persist(item);
            session.createQuery("delete from ItemEntity i where i.id = :id")
                    .setParameter("id", item.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        }



        @Test
        public void maxPriceTest() {
            Session session = EntityManagerUtil.getEntityManager();
            Query query = session.createQuery("select max(i.price) from ItemEntity i");
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


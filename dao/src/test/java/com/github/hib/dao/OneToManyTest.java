package com.github.hib.dao;

import com.github.hib.entity.*;
import com.github.hib.util.EntityManagerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

public class OneToManyTest {
    @Test
    public void saveTest() {

        CategoryEntity category = new CategoryEntity("fruits");
        ItemEntity item1 = new ItemEntity( "pomme", "sweet", 200, 300);
        category.getItems().add(item1);
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(category);
        em.flush();
        em.getTransaction().commit();
        Assertions.assertNotNull(category.getItems());
    }

    @Test
    public void readTest(){
        CategoryEntity category = new CategoryEntity("fruits");
        ItemEntity item1 = new ItemEntity( "pomme", "sweet", 200, 300);
        ItemEntity item2 = new ItemEntity( "pomme", "sweet", 200, 300);
        ItemEntity item3 = new ItemEntity( "pomme", "sweet", 200, 300);
        ItemEntity item4 = new ItemEntity( "pomme", "sweet", 200, 300);
        ItemEntity item5 = new ItemEntity( "pomme", "sweet", 200, 300);
        category.getItems().add(item1);
        category.getItems().add(item2);
        category.getItems().add(item3);
        category.getItems().add(item4);
        category.getItems().add(item5);

        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(category);
        em.flush();
        System.out.println(category.getNameCategory());
        em.getTransaction().commit();

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getItems());
    }

    @Test
    public void deleteTest(){
        CategoryEntity category = new CategoryEntity("fruits");
        ItemEntity item1 = new ItemEntity( 1,"pomme", "sweet", 200, 300);
        ItemEntity item2 = new ItemEntity( 2,"pomme", "sweet", 200, 300);
        ItemEntity item3 = new ItemEntity( 3,"pomme", "sweet", 200, 300);
        ItemEntity item4 = new ItemEntity( 4,"pomme", "sweet", 200, 300);
        ItemEntity item5 = new ItemEntity( 5,"pomme", "sweet", 200, 300);
        category.getItems().add(item1);
        category.getItems().add(item2);
        category.getItems().add(item3);
        category.getItems().add(item4);
        category.getItems().add(item5);
        System.out.println(category);
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(category.getItems().get(1));
        System.out.println(category.getItems());

        Assertions.assertNotNull(category);
        //todo

    }

}

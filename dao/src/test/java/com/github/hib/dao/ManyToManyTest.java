package com.github.hib.dao;

import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.entity.ItemEntity;
import com.github.hib.util.EntityManagerUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class ManyToManyTest {
    @Test
    public void saveTest() {
        ItemEntity item1 = new ItemEntity("Yulij", "Slabko",20,30);
        ItemEntity item2 = new ItemEntity("Sergey", "Kruk", 1,200);
        final ArrayList<ItemEntity> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        Address address = new Address("street","city","22222");
        Address address2 = new Address("street1","city1","22221");
        BookingEntity order1 = new BookingEntity( "Sofia",1,address, items);
        BookingEntity order2 = new BookingEntity( "Dani",12,address2, items);
        final ArrayList<BookingEntity> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        order1.getItems().addAll(items);
        order2.getItems().addAll(items);

        item1.getOrders().addAll(orders);
        item2.getOrders().addAll(orders);


        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(order1);
        em.persist(order2);
        em.getTransaction().commit();
        em.close();

        em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        order1 = em.find(BookingEntity.class, order1.getOrderId());
        order1.getItems().remove(1);
        em.getTransaction().commit();

    }

}

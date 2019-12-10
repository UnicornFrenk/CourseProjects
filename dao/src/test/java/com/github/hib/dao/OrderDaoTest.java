package com.github.hib.dao;

import com.github.config.DaoConfig;
import com.github.hib.dao.converters.BookingConverter;
import com.github.hib.dao.converters.ItemConverter;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.entity.ItemEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Item;
import com.github.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    SessionFactory sessionFactory;


    private BookingEntity saveOrder() {
        Session session = EntityManagerUtil.getEntityManager();
        List<ItemEntity> list = new ArrayList<>();
        Item item = itemDao.readItem(1);
        Item item1 = itemDao.readItem(2);
        list.add(ItemConverter.toEntity(item));
        list.add(ItemConverter.toEntity(item1));
        System.out.println(list);

        BookingEntity order = new BookingEntity("Sofia", 300, new Address(
                "street", "city","223333"), list);
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
        return order;
    }


    @Test
    public void read() {
        BookingEntity order = saveOrder();
        orderDao.readOrder(order.getOrderId());
        System.out.println(order);
        assertNotNull(order);
    }

    @Test
    public void update() {
        BookingEntity order = saveOrder();
        orderDao.updateOrder(order.getOrderId(),new Address("street","city",
                                                            "2222"));
        System.out.println(order);
        assertEquals("city",
                     orderDao.readOrder(order.getOrderId()).getDeliveryAddress().getCity());
    }

    @Test
    public void delete() {
        BookingEntity order = saveOrder();
        Integer orderId = order.getOrderId();
        assertNotNull(orderId);

        orderDao.deleteOrder(orderId);

        Order fromDb = orderDao.readOrder(orderId);
        assertNull(fromDb);
    }
}

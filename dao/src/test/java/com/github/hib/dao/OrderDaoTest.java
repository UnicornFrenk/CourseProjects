package com.github.hib.dao;

import com.github.config.DaoConfig;
import com.github.hib.dao.converters.BookingConverter;
import com.github.hib.dao.impl.DefaultOrderDao;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.util.EntityManagerUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class OrderDaoTest {


    @Autowired
    OrderDao orderDao;

    @Autowired
    SessionFactory sessionFactory;

    private BookingEntity saveOrder() {
        Session session = EntityManagerUtil.getEntityManager();
        BookingEntity order = new BookingEntity(1, 2, 300, null);
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
        return order;
    }

    @Test
    public void testSave() {
        BookingEntity testOrder = new BookingEntity(1, 1, 200, new Address());
        orderDao
                       .createOrder(BookingConverter.fromEntity(testOrder));
        System.out.println(testOrder);
        Assertions.assertNotNull(testOrder);
    }

    @Test
    public void deleteSession() {
        final BookingEntity order = saveOrder();
        orderDao.deleteOrder(order.getId());
        BookingEntity bookingEntity = EntityManagerUtil.getEntityManager()
                                                       .find(BookingEntity.class,
                                                             order.getId());
        Assertions.assertNull(bookingEntity);
    }

    @Test
    public void read() {
        BookingEntity order = saveOrder();
        orderDao.readOrder(order.getId());
        System.out.println(order);
        Assertions.assertNotNull(order);
    }


}

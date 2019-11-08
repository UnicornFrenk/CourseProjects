package com.github.hib.dao;

import com.github.hib.dao.converters.BookingConverter;
import com.github.hib.dao.impl.DefaultOrderDao;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.util.EntityManagerUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class OrderDaoTest {

        private BookingEntity saveOrder(){
                Session session = EntityManagerUtil.getEntityManager();
                BookingEntity order = new BookingEntity( 1,2,300,null);
                session.beginTransaction();
                session.save(order);
                session.getTransaction().commit();
                session.close();
                return order;
        }

        @Test
        public void testSave() {
                BookingEntity testOrder = new BookingEntity(1,1, 200,new Address());
                DefaultOrderDao.getInstance().createOrder(BookingConverter.fromEntity(testOrder));
                System.out.println(testOrder);
                Assertions.assertNotNull(testOrder);
        }

//        @Test
//        public void updateOrder() {
//
//                BookingEntity order = saveOrder();
//                DefaultOrderDao.getInstance().updateOrder(order.getId(), new Address("Minsk",null,null));
//                Assertions.assertEquals(order.getDeliveryAddress().getStreet(), "Minsk");
//        }

        @Test
        public void deleteSession() {
               final BookingEntity order = saveOrder();
                DefaultOrderDao.getInstance().deleteOrder(order.getId());
                BookingEntity bookingEntity =EntityManagerUtil.getEntityManager().find(BookingEntity.class, order.getId());
                Assertions.assertNull(bookingEntity);
        }

        @Test
        public void read(){
                BookingEntity order = saveOrder();
                DefaultOrderDao.getInstance().readOrder(order.getId());
                System.out.println(order);
                Assertions.assertNotNull(order);
        }



}

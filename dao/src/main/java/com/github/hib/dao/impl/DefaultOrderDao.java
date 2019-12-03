package com.github.hib.dao.impl;

import com.github.hib.dao.OrderDao;
import com.github.hib.dao.converters.BookingConverter;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderDao implements OrderDao {


    private static final Logger log = LoggerFactory.getLogger(
            DefaultItemDao.class);
    private final SessionFactory sessionFactory;


    public DefaultOrderDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer createOrder(Order order) {
        BookingEntity oEntity = new BookingEntity(order.getId(),
                                                  order.getItem_id(),
                                                  order.getUser_Id(),
                                                  order.getDeliveryAddress());
        final Session session = sessionFactory.getCurrentSession();
        session.save(oEntity);
        return oEntity.getId();
    }

    @Override
    public Order readOrder(int id) {
        BookingEntity oEntity = sessionFactory.getCurrentSession()
                                              .get(BookingEntity.class, id);
        return BookingConverter.fromEntity(oEntity);
    }

    @Override
    public Order getOrderByPersonLogin(String login) {
        BookingEntity oEntity = sessionFactory.getCurrentSession()
                                              .get(BookingEntity.class, login);
        return BookingConverter.fromEntity(oEntity);
    }

    @Override
    public void updateOrder(int id, Address address) {

        final Session session = sessionFactory.getCurrentSession();
        session.createQuery("update BookingEntity o set o.deliveryAddress = :address where o.user_Id = :id")
                             .setParameter("id", id)
                             .setParameter("address", address)
                             .executeUpdate();
    }

    @Override
    public void deleteOrder(int id) {
        final Session session = sessionFactory.getCurrentSession();
            session.createQuery("delete from BookingEntity b where b.id = :id")
                   .setParameter("id", id)
                   .executeUpdate();
    }

    @Override
    public List<Order> getAll() {
        final List<BookingEntity> orders = sessionFactory.getCurrentSession()
                                                            .createQuery(
                                                                    "from BookingEntity ")
                                                            .list();
        return orders.stream()
                     .map(BookingConverter::fromEntity)
                     .collect(Collectors.toList());
    }
}

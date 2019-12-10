package com.github.hib.dao.impl;

import com.github.hib.dao.OrderDao;
import com.github.hib.dao.converters.BookingConverter;
import com.github.hib.dao.converters.ItemConverter;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.entity.ItemEntity;
import com.github.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        BookingEntity bookingEntity = new BookingEntity(order.getUserName(),
                                                        order.getOrderId(),
                                                        order.getDeliveryAddress(),
                                                        order.getItemList());
        final Session session = sessionFactory.getCurrentSession();
        session.save(bookingEntity);
        return bookingEntity.getOrderId();
    }

    @Override
    public Order readOrder(int id) {
        BookingEntity oEntity = sessionFactory.getCurrentSession()
                                              .get(BookingEntity.class, id);
        return BookingConverter.fromEntity(oEntity);
    }

    @Override
    public Order getOrderByUserName(String userName) {
        BookingEntity bookingEntity = sessionFactory.getCurrentSession()
                                                    .get(BookingEntity.class,
                                                         userName);
        return BookingConverter.fromEntity(bookingEntity);
    }

    @Override
    public void updateOrder(int orderId, Address address) {

        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "update BookingEntity o set o.deliveryAddress.city = :city, " +
                        "o.deliveryAddress.street = :street, " +
                        "o.deliveryAddress.postalCode = :postalCode " +
                        "where o.orderId = :id")
               .setParameter("id", orderId)
               .setParameter("city", address.getCity())
               .setParameter("street", address.getStreet())
               .setParameter("postalCode", address.getPostalCode())
               .executeUpdate();
    }

    @Override
    public void deleteOrder(int orderId) {
        final Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from BookingEntity b where b.orderId = :id")
               .setParameter("id", orderId)
               .executeUpdate();
    }

    @Override
    public List<Order> getAll() {
        final List<BookingEntity> orders = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "from BookingEntity ")
                .list();
        return orders.stream()
                     .map(BookingConverter::fromEntity)
                     .collect(Collectors.toList());
    }

    @Override
    public List<Order> getPage(int page) {
        int pageSize = 3;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from BookingEntity b");

        return query.setMaxResults(pageSize)
                    .setFirstResult((page - 1) * pageSize)
                    .getResultList();
    }

    @Override
    public long getCountOfOrders() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession()
                                           .getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<BookingEntity> list = criteria.from(BookingEntity.class);
        if (list != null) {
            criteria.select(cb.count(list));
        }
        try {
            return sessionFactory.getCurrentSession()
                                 .createQuery(criteria)
                                 .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}

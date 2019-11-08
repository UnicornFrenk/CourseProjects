package com.github.hib.dao.impl;

import com.github.hib.dao.OrderDao;
import com.github.hib.dao.converters.BookingConverter;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Order;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderDao implements OrderDao {


    private static final Logger log = LoggerFactory.getLogger(
            DefaultItemDao.class);

    private static class SingletonHolder {
        static final OrderDao HOLDER_INSTANCE = new DefaultOrderDao();
    }

    public static OrderDao getInstance() {
        return DefaultOrderDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Integer createOrder(Order order) {
        BookingEntity oEntity = new BookingEntity(order.getId(),
                                                  order.getItem_id(),
                                                  order.getUser_Id(),
                                                  order.getDeliveryAddress());
        final Session session = EntityManagerUtil.getEntityManager();
        session.beginTransaction();
        session.save(oEntity);
        session.getTransaction().commit();
        return oEntity.getId();
    }

    @Override
    public Order readOrder(int id) {
        BookingEntity oEntity;
        try {
            oEntity = (BookingEntity) EntityManagerUtil.getEntityManager()
                                                       .createQuery(
                                                               "from BookingEntity o where o.id = :id")
                                                       .setParameter("id", id)
                                                       .getSingleResult();
        } catch (NoResultException e) {
            log.info("order not found by id{}", id);
            oEntity = null;
        }
        return BookingConverter.fromEntity(oEntity);
    }

    @Override
    public Order getOrderByPersonLogin(String login) {
        BookingEntity oEntity;
        try {
            oEntity = (BookingEntity) EntityManagerUtil.getEntityManager()
                                                       .createQuery(
                                                               "from BookingEntity o join PersonEntity p on p.id=o.user_Id where p.login = :login")
                                                       .setParameter("login",
                                                                     login)
                                                       .getSingleResult();
        } catch (NoResultException e) {
            log.info("person not found by login{}", login);
            oEntity = null;
        }
        return BookingConverter.fromEntity(oEntity);
    }

    @Override
    public void updateOrder(int id, Address address) {
        try {
            EntityManagerUtil.getEntityManager()
                             .createQuery(
                                     "update BookingEntity o set o.deliveryAddress = :address where o.user_Id = :id")
                             .setParameter("id", id)
                             .setParameter("address", address)
                             .executeUpdate();
        } catch (NoResultException e) {
            log.info("user not found by login{}", id);
        }
    }

    @Override
    public void deleteOrder(int id) {
        try {
            Session session = EntityManagerUtil.getEntityManager().getSession();
            session.beginTransaction();
            session.createQuery("delete from BookingEntity b where b.id = :id")
                   .setParameter("id", id)
                   .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (NoResultException e) {
            log.info("order not found by id{}", id);
        }
    }

    @Override
    public List<Order> getAll() {
        final List<BookingEntity> orders = EntityManagerUtil.getEntityManager()
                                                            .createQuery(
                                                                    "from BookingEntity ")
                                                            .list();
        return orders.stream()
                     .map(BookingConverter::fromEntity)
                     .collect(Collectors.toList());
    }
}

package com.github.impl;

import com.github.OrderService;
import com.github.hib.dao.OrderDao;
import com.github.hib.entity.Address;
import com.github.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultOrderService implements OrderService {

    private OrderDao orderDao;

    public DefaultOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public Integer createOrder(Order order) {
        return orderDao.createOrder(order);
    }

    @Override
    @Transactional
    public Order readOrder(int id) {
        return orderDao.readOrder(id);
    }

    @Override
    @Transactional
    public Order getOrderByPersonLogin(String login) {
        return orderDao.getOrderByUserName(login);
    }

    @Override
    @Transactional
    public void updateOrder(int id, Address address) {
        orderDao.updateOrder(id, address);
    }

    @Override
    @Transactional
    public void deleteOrder(int id) {
        orderDao.deleteOrder(id);

    }

    @Override
    @Transactional
    public List<Order> getAll() {
        return orderDao.getAll();
    }
}

package com.github.hib.dao;

import com.github.hib.entity.Address;
import com.github.model.Order;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Order order);

    Order readOrder(int orderId);
    Order getOrderByUserName(String userName);

    void updateOrder(int orderId, Address address);
    void deleteOrder(int orderId);
    List<Order> getAll();
    List<Order> getPage(int page);


    long getCountOfOrders();
}

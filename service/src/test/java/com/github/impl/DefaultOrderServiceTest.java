package com.github.impl;

import com.github.hib.dao.OrderDao;
import com.github.hib.entity.Address;
import com.github.hib.entity.BookingEntity;
import com.github.hib.entity.ItemEntity;
import com.github.hib.entity.PersonEntity;
import com.github.model.Order;
import com.github.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.omg.CORBA.ORB;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DefaultOrderServiceTest {

    @Mock
    OrderDao dao;
    @InjectMocks
    DefaultOrderService service;


    @Test
    public void testOrderNotExist() {
        when(dao.readOrder(1)).thenReturn(null);
        Order order = dao.readOrder(1);

        assertNull(order);
    }

    @Test
    public void readOrderTest() {
        Order order = new Order(1, null, null, null, null);
        when(dao.readOrder(1)).thenReturn(order);

        Integer orderFromDb = dao.readOrder(1).getId();
        Integer expName = 1;

        assertEquals(expName, orderFromDb);
    }

    @Test
    public void updateOrderTest() {
        doNothing().when(dao).updateOrder(anyInt(), any());
        service.updateOrder(1, new Address());
        verify(dao).updateOrder(1, new Address());
    }


    @Test
    public void deleteOrderTest() {
        doNothing().when(dao).deleteOrder(anyInt());
        service.deleteOrder(1);
        verify(dao).deleteOrder(1);
    }

    @Test
    public void getAllOrdersTest() {
        when(dao.getAll()).thenReturn(new ArrayList<Order>());
        List<Order> orders = service.getAll();
        System.out.println(orders);

        assertNotNull(orders);
    }
}

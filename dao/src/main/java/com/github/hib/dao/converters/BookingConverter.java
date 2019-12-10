package com.github.hib.dao.converters;

import com.github.hib.entity.BookingEntity;
import com.github.model.Order;

public class BookingConverter {
    public static BookingEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }
        final BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setOrderId(order.getOrderId());
        bookingEntity.setUserName(order.getUserName());
        bookingEntity.setItemList(order.getItemList());
        bookingEntity.setPrice(order.getPrice());
        bookingEntity.setDeliveryAddress(order.getDeliveryAddress());
        return bookingEntity;
    }

    public static Order fromEntity(BookingEntity bookingEntity) {
        if (bookingEntity == null) {
            return null;
        }
        return new Order(bookingEntity.getOrderId(), bookingEntity.getUserName(),
                         bookingEntity.getItemList(), bookingEntity.getPrice(),
                         bookingEntity.getDeliveryAddress());

    }
}

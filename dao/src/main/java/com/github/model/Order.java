package com.github.model;

import com.github.hib.entity.Address;
import com.github.hib.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Order {
    private Integer orderId;
    private String userName;
    private List<ItemEntity> itemList;
    private Integer price;
    private Address deliveryAddress;

    public Order(Integer orderId, String userName, List<ItemEntity> itemList,
                 Integer price, Address deliveryAddress) {
        this.orderId = orderId;
        this.userName = userName;
        this.itemList = itemList;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
    }


    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

}

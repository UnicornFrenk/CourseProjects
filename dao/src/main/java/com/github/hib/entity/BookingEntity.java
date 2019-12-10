package com.github.hib.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class BookingEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer orderId;
    @Column
    private String userName;
    @Column
    private Integer price;
    @Embedded
    private Address deliveryAddress;

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<ItemEntity> itemList = new ArrayList<>();

    public BookingEntity(String userName, Integer price, Address deliveryAddress,
                         List<ItemEntity> itemList) {
        this.userName = userName;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
        this.itemList = itemList;
    }

    public List<ItemEntity> getItems() {
        return itemList;
    }

    public void setItems(List<ItemEntity> items) {
        this.itemList = items;
    }

    @Override
    public String toString() {
        return "BookingEntity{" + "orderId=" + orderId + ", userName='" + userName + '\'' + ", totalPrice=" + price + ", deliveryAddress=" + deliveryAddress + ", itemList="  + '}';
    }
}

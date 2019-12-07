package com.github.hib.entity;


import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", unique = true)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name ="description" )
    private String description;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Integer price;


    public ItemEntity(Integer id, String name, String description, Integer quantity, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;

    }

    public ItemEntity(String name, String description, Integer quantity, Integer price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;

    }

    public ItemEntity(String name, String description, Integer quantity,
                      CategoryEntity category, Integer price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private CategoryEntity category;

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_item", joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "booking_id")}
    )
    private List<BookingEntity> orders = new ArrayList<>();

    public List<BookingEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<BookingEntity> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ItemEntity{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", quantity=" + quantity + ", price=" + price + ", category="  + '}';
    }
}

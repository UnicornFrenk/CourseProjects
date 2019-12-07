package com.github.model;

import lombok.*;

@Getter
@Setter
@Data
public class Item {
    private Integer id;
    private String itemName;
    private String itemDescription;
    private Integer itemQuantity;
    private Category itemCategory;
    private Integer priceForOne;
    public Item(){}

    public Item(Integer id, String itemName, String itemDescription, Integer itemQuantity, Integer priceForOne) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
        this.priceForOne = priceForOne;
    }

    public Item(Integer id, String itemName, String itemDescription,
                Integer itemQuantity, Category itemCategory, Integer priceForOne) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.priceForOne = priceForOne;
    }


    public Item(String itemName, String itemDescription, Integer itemQuantity, Integer priceForOne) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
        this.priceForOne = priceForOne;
    }

}

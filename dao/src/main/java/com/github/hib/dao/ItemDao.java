package com.github.hib.dao;

import com.github.model.Item;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ItemDao {
    Item createItem(Item item, Integer idCategory);

    Item readItem(String item_name);

    Item readItem(Integer id);

    void updateItemByName(Integer price, String name);

    void updateItemById(Integer price, Integer id);

    void deleteItem(Integer id);

    List<Item> getAll();

    List<Item> getPage(int page);


    long getCountOfItems();
}

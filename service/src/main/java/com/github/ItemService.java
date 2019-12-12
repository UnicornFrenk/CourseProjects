package com.github;

import com.github.model.Item;

import java.util.List;

public interface ItemService {

    Item createItem(Item item, String categoryName);

    Item readItem(String item_name);
    Item readItem(Integer id);


    void updateItemByName(Integer price, String name);
    void updateItemById(Integer price, Integer id);
    void deleteItem(Integer id);
    List<Item> getAll();
    List<Item> getPage(int page);
    Long countOfPage();


}

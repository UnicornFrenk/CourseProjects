package com.github.impl;

import com.github.ItemService;
import com.github.hib.dao.ItemDao;
import com.github.hib.dao.impl.DefaultItemDao;
import com.github.model.Item;

import java.util.List;
import java.util.ResourceBundle;

public class DefaultItemService implements ItemService {

    private ItemDao itemDao = DefaultItemDao.getInstance();

    private static class SingletonHolder {
        static final ItemService HOLDER_INSTANCE = new DefaultItemService();
    }

    public static ItemService getInstance() {
        return DefaultItemService
                .SingletonHolder
                .HOLDER_INSTANCE;
    }

    @Override
    public Item createItem(Item item, Integer categoryId) {
        return itemDao
                .createItem(item, categoryId);
    }

    @Override
    public Item readItem(String item_name) {
        return itemDao
                .readItem(item_name);
    }

    @Override
    public Item readItem(Integer id) {
        return itemDao.readItem(id);
    }

    @Override
    public void updateItem(Integer price, String  name) {
        itemDao.updateItem(price,name);
    }


    @Override
    public void updateItem(Integer price, Integer id) {
        itemDao.updateItem(price,id);
    }

    @Override
    public void deleteItem(Integer id) {
        itemDao.deleteItem(id);

    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    public List<Item> getPage(int page) {
        return itemDao.getPage(page);
    }

    public Long countOfPage(){
        return itemDao.getCountOfItems() / getMaxResult() + 1;
    }

    private Integer getMaxResult() {
        ResourceBundle resource = ResourceBundle.getBundle("const");
        return Integer.valueOf(resource.getString("amountOfOrdersOnPage"));
    }
}

package com.github.impl;

import com.github.ItemService;
import com.github.hib.dao.ItemDao;
import com.github.hib.dao.impl.DefaultItemDao;
import com.github.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultItemService implements ItemService {

    private ItemDao itemDao;

    public DefaultItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    @Transactional
    public Item createItem(Item item, Integer categoryId) {
        return itemDao.createItem(item, categoryId);
    }

    @Override
    @Transactional
    public Item readItem(String item_name) {
        return itemDao.readItem(item_name);
    }

    @Override
    @Transactional
    public Item readItem(Integer id) {
        return itemDao.readItem(id);
    }

    @Override
    @Transactional
    public void updateItemByName(Integer price, String name) {
        itemDao.updateItemByName(price, name);
    }


    @Override
    @Transactional
    public void updateItemById(Integer price, Integer id) {
        itemDao.updateItemById(price, id);
    }

    @Override
    @Transactional
    public void deleteItem(Integer id) {
        itemDao.deleteItem(id);

    }

    @Override
    @Transactional
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    @Transactional
    public List<Item> getPage(int page) {
        return itemDao.getPage(page);
    }

    @Transactional
    public Long countOfPage() {
        long countOfItems = itemDao.getCountOfItems();

        long l = countOfItems % getMaxResult();
        if (l != 0) {
            return countOfItems / getMaxResult() + 1;
        }
        return countOfItems / getMaxResult();
    }

    private Integer getMaxResult() {
        ResourceBundle resource = ResourceBundle.getBundle("const");
        return Integer.valueOf(resource.getString("amountOfOrdersOnPage"));
    }
}

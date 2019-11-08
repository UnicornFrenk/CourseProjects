package com.github.hib.dao;

import com.github.hib.dao.converters.CategoryConverter;
import com.github.hib.dao.impl.DefaultCategoryDao;
import com.github.hib.dao.impl.DefaultItemDao;
import com.github.hib.entity.CategoryEntity;
import com.github.hib.entity.ItemEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Category;
import com.github.model.Item;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ItemDaoTest {

    CategoryDao categoryDao = DefaultCategoryDao.getInstance();
    ItemDao itemDao = DefaultItemDao.getInstance();

    private ItemEntity saveItem() {
        Session session = EntityManagerUtil.getEntityManager();
        ItemEntity item = new ItemEntity("kiwi", "kiwi", 300, 300);
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Test
    public void createItem() {
        Item testItem = new Item("pomme", "pomme", 3, 200);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setNameCategory("apple");
        Category category = categoryDao.createCategory(CategoryConverter.fromEntity(categoryEntity));
        Integer categoryId = category.getIdCategory();

        Integer id = itemDao.createItem(testItem, categoryId).getId();

        Item item = itemDao.readItem(id);

        String itemCategory = item.getItemCategory();
        Assertions.assertNotNull(testItem);
        Assertions.assertNotNull(itemCategory);
    }

    @Test
    public void readItem() {
        final ItemEntity itemEntity = saveItem();
        Item fromDB = DefaultItemDao.getInstance().readItem(itemEntity.getId());

        Assertions.assertNotNull(fromDB);
    }

    @Test
    public void updateItem() {
        ItemEntity testItem = saveItem();

        DefaultItemDao.getInstance().updateItem(200, testItem.getId());

        Item item = DefaultItemDao.getInstance().readItem(testItem.getId());
        Assertions.assertEquals((Integer) 200, item.getPriceForOne());
    }

    @Test
    public void deleteSession() {
        final ItemEntity item = saveItem();
        System.out.println(item);

        DefaultItemDao.getInstance().deleteItem(item.getId());

        ItemEntity itemEntity = EntityManagerUtil.getEntityManager().find(ItemEntity.class, item.getId());
        System.out.println(itemEntity);
        Assertions.assertNull(itemEntity);
    }

    @Test
    public void allItem() {
        List<ItemEntity> list = new ArrayList<>();
        ItemEntity i1 = saveItem();
        ItemEntity i2 = saveItem();
        ItemEntity i3 = saveItem();
        list.add(i1);
        list.add(i2);
        list.add(i3);
        List<Item> expected = DefaultItemDao.getInstance().getAll();
        System.out.println(expected);
        Assertions.assertNotNull(expected);
    }
}
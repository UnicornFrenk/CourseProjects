package com.github.hib.dao;

import com.github.config.DaoConfig;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class ItemDaoTest {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ItemDao itemDao;

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

        Category itemCategory = item.getItemCategory();
        Assertions.assertNotNull(testItem);
        Assertions.assertNotNull(itemCategory);
    }

    @Test
    public void readItem() {
        final ItemEntity itemEntity = saveItem();
        Item fromDB = itemDao.readItem(itemEntity.getId());

        Assertions.assertNotNull(fromDB);
    }

    @Test
    public void updateItem() {
        ItemEntity testItem = saveItem();

       itemDao.updateItemById(200, testItem.getId());

        Item item = itemDao.readItem(testItem.getId());
        Assertions.assertEquals((Integer) 200, item.getPriceForOne());
    }

//    @Test
//    public void updateItemByName() {
//        ItemEntity testItem = saveItem();
//
//        itemDao.updateItemByName(20, testItem.getName());
//
//        Item item = itemDao.readItem(testItem.getName());
//        Assertions.assertEquals((Integer) 20, item.getPriceForOne());
//    }



    @Test
    public void allItem() {
        List<ItemEntity> list = new ArrayList<>();
        ItemEntity i1 = saveItem();
        ItemEntity i2 = saveItem();
        ItemEntity i3 = saveItem();
        list.add(i1);
        list.add(i2);
        list.add(i3);
        List<Item> expected = itemDao.getAll();
        System.out.println(expected);
        Assertions.assertNotNull(expected);
    }
}
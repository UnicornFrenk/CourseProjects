package com.github.hib.dao;

import com.github.config.DaoConfig;
import com.github.hib.entity.CategoryEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class CategoryDaoTest {

@Autowired
CategoryDao categoryDao;

@Autowired
    SessionFactory sessionFactory;


    private CategoryEntity saveCategory() {
        Session session = EntityManagerUtil.getEntityManager();
        String name = "kiwi" + ThreadLocalRandom.current().nextInt();
        CategoryEntity category = new CategoryEntity(name);
        session.beginTransaction();
        session.save(category);
        session.getTransaction().commit();
        session.close();

        return category;
    }

    @Test
    public void read() {
        final CategoryEntity category = saveCategory();
        categoryDao.readCategory(category.getNameCategory());

        Assertions.assertNotNull(category.getNameCategory());
    }

    @Test
    public void updateCategory() {
        CategoryEntity category = saveCategory();

        categoryDao.updateCategory("tasty", category.getIdCategory());

        Category categoryFromDb =
                categoryDao.readCategory("tasty");
        System.out.println(categoryFromDb);
        Assertions.assertEquals("tasty", categoryFromDb.getNameCategory());
    }

    @Test
    public void createTest() {
        Category testCategory =categoryDao.createCategory(new Category(null, "look"));
        System.out.println(testCategory);

        Assertions.assertNotNull(testCategory);
    }

    @Test
    public void getAll() {
        final CategoryEntity category = saveCategory();
        List<Category> list = categoryDao.getAll();
        System.out.println(list);

        Assertions.assertNotNull(list);
    }


}


package com.github.hib.dao;

import com.github.hib.dao.impl.DefaultCategoryDao;
import com.github.hib.entity.CategoryEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Category;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CategoryDaoTest {


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
        DefaultCategoryDao.getInstance().readCategory(category.getNameCategory());

        Assertions.assertNotNull(category.getNameCategory());
    }

    @Test
    public void updateCategory() {
        CategoryEntity category = saveCategory();

        DefaultCategoryDao.getInstance().updateCategory("tasty", category.getIdCategory());

        Category categoryFromDb =
                DefaultCategoryDao.getInstance().readCategory("tasty");
        System.out.println(categoryFromDb);
        Assertions.assertEquals("tasty", categoryFromDb.getNameCategory());
    }

    //    @Test //todo
//    public void deleteSession() {
//        final CategoryEntity category = saveCategory();
//        DefaultCategoryDao.getInstance().deleteCategory(category.getIdCategory());
//        CategoryEntity categoryEntity =EntityManagerUtil.getEntityManager().find(CategoryEntity.class, category.getIdCategory());
//        Assertions.assertNull(categoryEntity);
//    }
    @Test
    public void createTest() {
        Category testCategory = DefaultCategoryDao.getInstance().createCategory(new Category(null, "look"));
        System.out.println(testCategory);

        Assertions.assertNotNull(testCategory);
    }

    @Test
    public void getAll() {
        final CategoryEntity category = saveCategory();
        List<Category> list = DefaultCategoryDao.getInstance().getAll();
        System.out.println(list);

        Assertions.assertNotNull(list);
    }


}


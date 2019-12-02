package com.github.hib.dao.impl;

import com.github.hib.dao.CategoryDao;
import com.github.hib.dao.converters.CategoryConverter;
import com.github.hib.dao.converters.PersonConverter;
import com.github.hib.entity.CategoryEntity;
import com.github.hib.entity.ItemEntity;
import com.github.hib.entity.PersonEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Category;
import com.github.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCategoryDao implements CategoryDao {

    private static final Logger log = LoggerFactory.getLogger(DefaultItemDao.class);
    private final SessionFactory sessionFactory;

    public DefaultCategoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Category createCategory(Category category) {
        CategoryEntity cEntity = new CategoryEntity(category.getNameCategory());
        final Session session = EntityManagerUtil.getEntityManager();
        session.beginTransaction();
        session.save(cEntity);
        session.getTransaction().commit();
        return CategoryConverter.fromEntity(cEntity);
    }

    @Override
    public Category readCategory(String category_name) {
        CategoryEntity cEntity;
        try {
            cEntity = (CategoryEntity) EntityManagerUtil.getEntityManager().createQuery("from CategoryEntity c where c.nameCategory = :name").setParameter("name", category_name).getSingleResult();
        } catch (NoResultException e) {
            log.info("category not found by category_name{}", category_name);
            cEntity = null;
        }
        return CategoryConverter.fromEntity(cEntity);
    }

    @Override
    public void updateCategory(String name, int id) {
        try {
            Session session = EntityManagerUtil.getEntityManager().getSession();
            session.beginTransaction();
            session.createQuery("update CategoryEntity c set c.nameCategory = :name where c.id = :id")
                    .setParameter("name", name)
                    .setParameter("id",id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            log.info("category not found by id{}", id);
        }
    }

    @Override
    public void deleteCategory(String name) {
        try {
           Session session =  EntityManagerUtil.getEntityManager().getSession();
           session.beginTransaction();
           session
                    .createQuery("delete from CategoryEntity c where c.nameCategory = :name")
                    .setParameter("name", name)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            log.info("category not found by name{}", name);
        }
    }

    @Override
    public List<Category> getAll() {
        final List<CategoryEntity> categoryList = EntityManagerUtil.getEntityManager().createQuery("from CategoryEntity ").list();
        return categoryList.stream().map(CategoryConverter::fromEntity).collect(Collectors.toList());
    }
}


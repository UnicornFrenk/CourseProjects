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

    private static final Logger log = LoggerFactory.getLogger(
            DefaultItemDao.class);
    private final SessionFactory sessionFactory;

    public DefaultCategoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Category createCategory(Category category) {
        CategoryEntity cEntity =
                CategoryConverter.toEntity(category);
        final Session session = sessionFactory.getCurrentSession();
        session.save(cEntity);
        return CategoryConverter.fromEntity(cEntity);
    }

    @Override
    public Category readCategory(Integer idCategory) {
        final CategoryEntity cEntity = sessionFactory
                .getCurrentSession()
                .get(CategoryEntity.class, idCategory);
        return CategoryConverter.fromEntity(cEntity);
    }

    @Override
    public void updateCategory(String name, int id) {
        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "update CategoryEntity c set c.nameCategory = :name where c.id = :id")
               .setParameter("name", name)
               .setParameter("id", id)
               .executeUpdate();
    }

    @Override
    public void deleteCategory(String name) {

        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "delete from CategoryEntity c where c.nameCategory = :name")
               .setParameter("name", name)
               .executeUpdate();
    }

    @Override
    public List<Category> getAll() {
        final List<CategoryEntity> categoryList = sessionFactory.getCurrentSession()
                                                                .createQuery(
                                                                        "from CategoryEntity ")
                                                                .list();
        return categoryList.stream()
                           .map(CategoryConverter::fromEntity)
                           .collect(Collectors.toList());
    }
}


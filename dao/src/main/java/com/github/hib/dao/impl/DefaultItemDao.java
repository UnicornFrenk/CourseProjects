package com.github.hib.dao.impl;

import com.github.hib.dao.ItemDao;
import com.github.hib.dao.converters.ItemConverter;
import com.github.hib.entity.CategoryEntity;
import com.github.hib.entity.ItemEntity;
import com.github.hib.util.EntityManagerUtil;
import com.github.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultItemDao implements ItemDao {

    private static final Logger log = LoggerFactory.getLogger(
            DefaultItemDao.class);
    private final SessionFactory sessionFactory;

    public DefaultItemDao(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Item createItem(Item item1, Integer categoryId) {
        ItemEntity itemEntity = ItemConverter.toEntity(item1);
        itemEntity.setId(item1.getId());
        itemEntity.setName(item1.getItemName());
        itemEntity.setDescription(item1.getItemDescription());
        itemEntity.setQuantity(item1.getItemQuantity());
        itemEntity.setPrice(item1.getPriceForOne());


        final Session session = sessionFactory.getCurrentSession();
        CategoryEntity category = session.get(CategoryEntity.class, categoryId);
        itemEntity.setCategory(category);

        category.getItems().add(itemEntity);
        session.save(itemEntity);
        return ItemConverter.fromEntity(itemEntity);
    }

    @Override
    public Item readItem(String item_name) {
        final ItemEntity iEntity =
                sessionFactory.getCurrentSession().get(ItemEntity.class, item_name);
        return ItemConverter.fromEntity(iEntity);
    }

    @Override
    public Item readItem(Integer id) {
        final ItemEntity itemEntity =
        sessionFactory.getCurrentSession().get(ItemEntity.class, id);
        return ItemConverter.fromEntity(itemEntity);
    }

    @Override
    public void updateItem(Integer price, String name) {
        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "update ItemEntity set price= :price where name =:name")
               .setParameter("price", price)
               .setParameter("name", name)
               .executeUpdate();
    }


    @Override
    public void updateItem(Integer price, Integer id) {
        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "update ItemEntity set price= :price where id =:id")
               .setParameter("price", price)
               .setParameter("id", id)
               .executeUpdate();
    }


    @Override
    public void deleteItem(Integer id) {

        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(
                "delete from ItemEntity i where i.id = :id")
               .setParameter("id", id)
               .executeUpdate();
    }

    @Override
    public List<Item> getAll() {
        final List<ItemEntity> itemEntities = sessionFactory.getCurrentSession()
                                                               .createQuery(
                                                                       "from ItemEntity ")
                                                               .list();
        return itemEntities.stream()
                           .map(ItemConverter::fromEntity)
                           .collect(Collectors.toList());

    }


    public List<Item> getPage(int page) {
        int pageSize = 3;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from ItemEntity i");

        return query.setMaxResults(pageSize)
                    .setFirstResult((page - 1) * pageSize)
                    .getResultList();
    }

    @Override
    public long getCountOfItems() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession()
                                              .getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<ItemEntity> list = criteria.from(ItemEntity.class);
        if (list != null) {
            criteria.select(cb.count(list));
        }
        try {
            return sessionFactory.getCurrentSession()
                                    .createQuery(criteria)
                                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}

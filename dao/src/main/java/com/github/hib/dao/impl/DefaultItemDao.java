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
        ItemEntity item = new ItemEntity();
        item.setId(item1.getId());
        item.setName(item1.getItemName());
        item.setDescription(item1.getItemDescription());
        item.setQuantity(item1.getItemQuantity());
        item.setPrice(item1.getPriceForOne());


        Session session = EntityManagerUtil.getEntityManager();
        CategoryEntity category = session.get(CategoryEntity.class, categoryId);
        item.setCategory(category);

        category.getItems().add(item);
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        System.out.println(item);
        session.close();
        return ItemConverter.fromEntity(item);
    }

    @Override
    public Item readItem(String item_name) {
        ItemEntity iEntity;
        try {
            iEntity = readItemEntity(EntityManagerUtil.getEntityManager(),
                                     item_name);
        } catch (NoResultException e) {
            log.info("item not found by name{}", item_name);
            iEntity = null;
        }
        return ItemConverter.fromEntity(iEntity);
    }

    @Override
    public Item readItem(Integer id) {
        Item item;
        try {
            Session session = EntityManagerUtil.getEntityManager();
            session.beginTransaction();
            ItemEntity iEntity = session.get(ItemEntity.class, id);
            item = ItemConverter.fromEntity(iEntity);
            session.getTransaction().commit();
        } catch (NoResultException e) {
            log.info("item not found by id{}", id);
            item = null;
        }
        return item;
    }

    private ItemEntity readItemEntity(Session entityManager, String item_name) {
        return (ItemEntity) entityManager.createQuery(
                "from ItemEntity i where i.name = :name")
                                         .setParameter("name", item_name)
                                         .getSingleResult();
    }


    @Override
    public void updateItem(Integer price, String name) {
        Session session = EntityManagerUtil.getEntityManager();
        session.beginTransaction();
        ItemEntity itemEntity = readItemEntity(session, name);
        itemEntity.setPrice(price);
        session.saveOrUpdate(itemEntity);
        session.getTransaction().commit();
        session.close();


//   hql
//        try (Session session = EntityManagerUtil.getEntityManager().getSession()) {
//            session.beginTransaction();
//            Integer id = DefaultItemDao.getInstance().readItem(name).getId();
//            ItemEntity itemFromDB = session.get(ItemEntity.class, id);
//            itemFromDB.setPrice(price);
//            session.getTransaction().commit();
//        } catch (RollbackException e) {
//            log.warn("price: {}, name: {} ", price, name, e);
//        }

    }

    @Override
    public void updateItem(Integer price, Integer id) {
        Session session = EntityManagerUtil.getEntityManager();
        session.beginTransaction();
        ItemEntity itemEntity = session.get(ItemEntity.class, id);
        itemEntity.setPrice(price);
        session.saveOrUpdate(itemEntity);
        session.getTransaction().commit();
        session.close();
    }


    @Override
    public void deleteItem(Integer id) {

        try {
            Session session = EntityManagerUtil.getEntityManager().getSession();
            session.beginTransaction();
            session.createQuery("delete from ItemEntity i where i.id = :id")
                   .setParameter("id", id)
                   .executeUpdate();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            log.info("item not found by id{}", id);
        }
//        final ItemEntity item = new ItemEntity();
//        Session session = EntityManagerUtil.getEntityManager();
//        session.save(item);
//        session.beginTransaction();
//        session.get(ItemEntity.class, item.getId());
//        session.delete(item);
//        session.getTransaction().commit();
    }

    @Override
    public List<Item> getAll() {
        final List<ItemEntity> itemEntities = EntityManagerUtil.getEntityManager()
                                                               .createQuery(
                                                                       "from ItemEntity ")
                                                               .list();
        return itemEntities.stream()
                           .map(ItemConverter::fromEntity)
                           .collect(Collectors.toList());

    }


    public List<Item> getPage(int page) {
        int pageSize = 3;
        Session session = EntityManagerUtil.getEntityManager();
        Query query = session.createQuery("from ItemEntity i");

        return query.setMaxResults(pageSize)
                    .setFirstResult((page - 1) * pageSize)
                    .getResultList();
    }

    @Override
    public long getCountOfItems() {
        CriteriaBuilder cb = EntityManagerUtil.getEntityManager()
                                              .getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<ItemEntity> list = criteria.from(ItemEntity.class);
        if (list != null) {
            criteria.select(cb.count(list));
        }
        try {
            return EntityManagerUtil.getEntityManager()
                                    .createQuery(criteria)
                                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}

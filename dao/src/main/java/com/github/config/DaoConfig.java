package com.github.config;

import com.github.hib.dao.CategoryDao;
import com.github.hib.dao.ItemDao;
import com.github.hib.dao.OrderDao;
import com.github.hib.dao.PersonDao;
import com.github.hib.dao.impl.DefaultCategoryDao;
import com.github.hib.dao.impl.DefaultItemDao;
import com.github.hib.dao.impl.DefaultOrderDao;
import com.github.hib.dao.impl.DefaultPersonDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(HibernateConfig.class)
@EnableTransactionManagement
@EnableJpaRepositories("com.github.hib.repository")
public class DaoConfig {

    private SessionFactory sessionFactory;


    public DaoConfig(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Bean
    public CategoryDao categoryDao() {
        return new DefaultCategoryDao(sessionFactory);
    }

    @Bean
    public ItemDao itemDao() {
        return new DefaultItemDao(sessionFactory);
    }

    @Bean
    public OrderDao orderDao() {
        return new DefaultOrderDao(sessionFactory);
    }
    
    @Bean
    public PersonDao personDao() {
        return new DefaultPersonDao(sessionFactory);
    }


}

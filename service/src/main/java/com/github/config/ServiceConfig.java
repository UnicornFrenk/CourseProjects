package com.github.config;

import com.github.*;
import com.github.impl.*;
import com.github.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {


    private DaoConfig daoConfig;

    @Autowired
    public ServiceConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    @Bean
    public CategoryService categoryService(){
        return new DefaultCategoryService(daoConfig.categoryDao());
    }

    @Bean
    public ItemService itemService(){
        return new DefaultItemService(daoConfig.itemDao());
    }

    @Bean
    public OrderService orderService(){
        return new DefaultOrderService(daoConfig.orderDao());
    }

    @Bean
    public PersonService personService(){
        return new DefaultPersonService(daoConfig.personDao());
    }

}

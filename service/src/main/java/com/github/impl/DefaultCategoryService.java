package com.github.impl;

import com.github.CategoryService;
import com.github.hib.dao.CategoryDao;
import com.github.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class DefaultCategoryService implements CategoryService {

    private CategoryDao categoryDao;

    public DefaultCategoryService(CategoryDao categoryDao) {
     this.categoryDao = categoryDao;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryDao
                .createCategory(category);
    }

    @Override
    @Transactional
    public Category readCategory(String category_name) {
        return categoryDao
                .readCategory(category_name);
    }

    @Override
    @Transactional
    public void updateCategory(String name, int id) {
        categoryDao
                .updateCategory(name,id);
    }

    @Override
    @Transactional
    public void deleteCategory(String name) {
        categoryDao.deleteCategory(name);
    }

    @Override
    @Transactional
    public List<Category> getAll() {
       return categoryDao.getAll();
    }


    }

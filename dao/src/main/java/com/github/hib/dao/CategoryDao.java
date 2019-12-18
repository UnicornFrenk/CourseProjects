package com.github.hib.dao;

import com.github.model.Category;
import com.github.model.Item;

import java.util.List;

public interface CategoryDao {
    Category createCategory(Category category);

    Category readCategory(Integer idCategory);

    void updateCategory(String name, int id);
    void deleteCategory(String name);
    List<Category> getAll();


}

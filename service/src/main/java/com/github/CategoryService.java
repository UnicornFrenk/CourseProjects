package com.github;

import com.github.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category readCategory(Integer idCategory);

    void updateCategory(String name, int id);
    void deleteCategory(String name);
    List<Category> getAll();
}

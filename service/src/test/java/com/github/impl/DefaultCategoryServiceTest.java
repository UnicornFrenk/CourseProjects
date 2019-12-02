package com.github.impl;

import com.github.hib.dao.CategoryDao;
import com.github.model.Category;
import com.github.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class DefaultCategoryServiceTest {

    @Mock
    CategoryDao dao;

    @InjectMocks
    DefaultCategoryService service;

    @Test
    public void createCategoryTest() {
        Category category = new Category(null, "sweets");
        service.createCategory(category);
        String categoryFromDb = category.getNameCategory();
        String exp = "sweets";

        assertEquals(exp, categoryFromDb);
    }

    @Test
    public void readCategoryTest() {
        Category category = new Category(null, "apple");
        when(dao.readCategory("apple")).thenReturn(category);

        Category catFromDb = dao.readCategory("apple");
        String expName = "apple";

        assertEquals(expName, catFromDb.getNameCategory());
    }

    @Test
    public void updateCategoryTest() {   //todo
        doNothing().when(dao).updateCategory(anyString(), anyInt());
        service.updateCategory("apple", 1);

        verify(dao).updateCategory("apple", 1);}


    @Test
    public void deleteCategoryTest() {

        when(dao.readCategory("apple")).thenReturn(new Category());
        Integer id = service.readCategory("apple").getIdCategory();

        assertNull(id);
    }

    @Test
    public void getAllCategoriesTest() {
        when(dao.getAll()).thenReturn(new ArrayList<Category>());
        List<Category> categories = service.getAll();
        System.out.println(categories);

        assertNotNull(categories);
    }

    @Test
    public void testCategoryNotExist() {
        when(dao.readCategory("fruits")).thenReturn(null);

        Category name = service.readCategory("fruits");

        assertNull(name);
    }

}

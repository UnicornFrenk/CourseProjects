package com.github.impl;

import com.github.hib.dao.ItemDao;
import com.github.model.Category;
import com.github.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DefaultItemServiceTest {

    @Mock
    ItemDao dao;
    @InjectMocks
    DefaultItemService service;


    @Test
    public void createItemTest() {
        Item item = new Item(3, "nuts", "nuts",1000, new Category(3,"fruits"),
                             500);
        when(dao.createItem(item, 3)).thenReturn(item);

        Item itemFromDb = service.createItem(item, 3);
        System.out.println(itemFromDb);
        assertNotNull(itemFromDb);
        assertEquals(item.getItemName(), itemFromDb.getItemName());
    }

    @Test
    public void readItemTest() {
        when(dao.readItem("apple")).thenReturn(new Item("apple", null, null, null));

        Item itemFromDb = service.readItem("apple");

        assertNotNull(itemFromDb);
        assertEquals("apple", itemFromDb.getItemName());
    }

    @Test
    public void updateItemTest() {

        doNothing().when(dao).updateItemByName(anyInt(), anyString());

        service.updateItemByName(1, "apple");

        verify(dao).updateItemByName(1, "apple");
    }

    @Test
    public void updateItemId() {

        doNothing().when(dao).updateItemById(anyInt(), anyInt());

        service.updateItemById(300, 7);

        verify(dao).updateItemById(300, 7);
    }

    @Test
    public void deleteItemTest() {

        doNothing().when(dao).deleteItem(anyInt());
        service.deleteItem(1);
        verify(dao).deleteItem(1);
    }

    @Test
    public void getAllItemsTest() {
        when(dao.getAll()).thenReturn(new ArrayList<>());

        List<Item> items = service.getAll();

        assertNotNull(items);
    }

    @Test
    public void testItemNotExist() {

        when(dao.readItem("nuts")).thenReturn(null);

        Item item = dao.readItem("nuts");

        assertNull(item);
    }

    @Test
    void getPage() {
        List<Item> forTest = new ArrayList<>();
        Item testItem = new Item("pomme", "pomme",3,200);
        Item testItem1 = new Item("pomme1", "pomme",3,200);
        Item testItem2= new Item("pomme2", "pomme",3,200);
        Item testItem3 = new Item("pomme1", "pomme",3,200);
        Item testItem4= new Item("pomme2", "pomme",3,200);
        forTest.add(testItem);
        forTest.add(testItem1);
        forTest.add(testItem2);
        forTest.add(testItem3);
        forTest.add(testItem4);
        when(dao.getPage(1)).thenReturn(forTest);
        List<Item> items = service.getAll();
    }
}
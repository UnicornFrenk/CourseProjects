package com.github.hib.dao.converters;

import com.github.hib.entity.CategoryEntity;
import com.github.hib.entity.ItemEntity;
import com.github.model.Category;
import com.github.model.Item;

public class ItemConverter {
    public static ItemEntity toEntity(Item item) {
        if (item == null) {
            return null;
        }
        final ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(item.getId());
        itemEntity.setName(item.getItemName());
        itemEntity.setDescription(item.getItemDescription());
        itemEntity.setQuantity(item.getItemQuantity());
        itemEntity.setPrice(item.getPriceForOne());
        itemEntity.setCategory(new CategoryEntity());
        return itemEntity;
    }

    public static Item fromEntity(ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        }
        CategoryEntity category2 = itemEntity.getCategory();
        String category = null;
        if (category2 != null) {

            category = CategoryConverter.fromEntity(category2).getNameCategory();
        }
        return new Item(itemEntity.getId(), itemEntity.getName(),
                        itemEntity.getDescription(), itemEntity.getQuantity(),
                        category, itemEntity.getPrice());

    }
}

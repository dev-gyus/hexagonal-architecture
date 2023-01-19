package com.example.hexagonal.application.item;

import com.example.hexagonal.domain.item.Item;
import org.bson.types.ObjectId;

public interface ItemService {
    Item saveItem(ItemCommandParams params);

    Item findItemById(String id);

    void updateItem(String id, ItemCommandParams params);

}

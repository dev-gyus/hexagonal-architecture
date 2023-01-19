package com.example.hexagonal.domain.item;

import com.example.hexagonal.application.item.ItemCommandParams;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface QueryItemRepository {
    void updateItem(String id, ItemCommandParams params);

    Item findByName(String name);
}

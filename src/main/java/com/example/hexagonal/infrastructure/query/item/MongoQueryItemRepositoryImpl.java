package com.example.hexagonal.infrastructure.query.item;

import com.example.hexagonal.application.item.ItemCommandParams;
import com.example.hexagonal.domain.item.Item;
import com.example.hexagonal.domain.item.ItemRepository;
import com.example.hexagonal.domain.item.QueryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MongoQueryItemRepositoryImpl implements QueryItemRepository {
    private final MongoOperations mongoOperations;
    @Override
    public void updateItem(String id, ItemCommandParams params) {
        mongoOperations.updateFirst(Query.query(where("id").is(id)),
                Update.update("name", params.getName()).set("price", params.getPrice()),
                "item");
    }

    @Override
    public Item findByName(String name) {
        return mongoOperations.findOne(Query.query(where("name").is(name)), Item.class);
    }
}

package com.example.hexagonal.infrastructure.query.item;

import com.example.hexagonal.application.item.ItemCommandParams;
import com.example.hexagonal.domain.item.Item;
import com.example.hexagonal.domain.item.ItemQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MongoItemQueryRepositoryImpl implements ItemQueryRepository {
    private final MongoOperations mongoOperations;
    @Override
    public void updateItem(Long id, ItemCommandParams params) {
        mongoOperations.updateFirst(Query.query(where("id").is(id)),
                Update.update("name", params.getName()).set("price", params.getPrice()),
                "item");
    }

    @Override
    public Item findByName(String name) {
        return mongoOperations.findOne(Query.query(where("name").is(name)), Item.class);
    }
}

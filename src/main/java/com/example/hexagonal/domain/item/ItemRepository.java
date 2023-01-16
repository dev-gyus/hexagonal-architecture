package com.example.hexagonal.domain.item;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Long>, ItemQueryRepository {
}

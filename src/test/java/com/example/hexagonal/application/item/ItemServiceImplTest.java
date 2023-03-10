package com.example.hexagonal.application.item;

import com.example.hexagonal.application.exception.NotFoundException;
import com.example.hexagonal.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.convert.SimpleTypeInformationMapper;
import org.springframework.data.convert.TypeAliasAccessor;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.Alias;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ItemServiceImplTest {
    @Autowired
    ItemService itemService;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager platformTransactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
            return new MongoTransactionManager(mongoDatabaseFactory);
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    void ?????????_??????_??????() {
        // given
        String name = "ite22m";
        Integer price = 100300;
        ItemCommandParams params = new ItemCommandParams(1L, name, price);
        // when
        Item item = itemService.saveItem(params);
        // then
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getPrice()).isEqualTo(price);
    }

    @Test
    @Transactional
    void ?????????_??????_??????_??????_??????() {
        // given
        String name = "item1";
        Integer price = 10000;
        ItemCommandParams params = new ItemCommandParams(1L, name, price);
        ItemCommandParams params2 = new ItemCommandParams(2L, name, price);
        // when
        Item item = itemService.saveItem(params);
        Assertions.assertThatThrownBy(() -> itemService.saveItem(params2))
                .isInstanceOf(DataAccessException.class);
        // then
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getPrice()).isEqualTo(price);
    }

    @Test
    @Transactional
    void ?????????_??????_??????_??????() {
        // given
        String name = "item1";
        Integer price = 10000;
        ItemCommandParams params = new ItemCommandParams(1L, name, price);
        Item item = itemService.saveItem(params);
        // when
        Item foundItem = itemService.findItemById(item.getId());
        // then
        assertThat(foundItem.getId()).isEqualTo(item.getId());
        assertThat(foundItem.getName()).isEqualTo(item.getName());
        assertThat(foundItem.getPrice()).isEqualTo(item.getPrice());
    }

    @Test
    @Transactional
    void ?????????_??????_??????_??????_????????????_??????() {
        // given
        String name = "item1";
        Integer price = 10000;
        ItemCommandParams params = new ItemCommandParams(1L, name, price);
        Item item = itemService.saveItem(params);
        // when
        Assertions.assertThatThrownBy(() -> itemService.findItemById(item.getId() + 1))
                .isInstanceOf(NotFoundException.class);
        // then
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getPrice()).isEqualTo(price);
    }

    @Test
    @Transactional
    void ?????????_??????_??????() {
        // given
        String name = "item1";
        Integer price = 10000;
        ItemCommandParams params = new ItemCommandParams(1L, name, price);
        Item item = itemService.saveItem(params);
        // when
        ItemCommandParams modifyParams = new ItemCommandParams(1L, "item2", 20000);
        itemService.updateItem(item.getId(), modifyParams);
        Item foundItem = itemService.findItemById(item.getId());
        // then
        assertThat(foundItem.getId()).isEqualTo(item.getId());
        assertThat(foundItem.getName()).isEqualTo(modifyParams.getName());
        assertThat(foundItem.getPrice()).isEqualTo(modifyParams.getPrice());
    }
}
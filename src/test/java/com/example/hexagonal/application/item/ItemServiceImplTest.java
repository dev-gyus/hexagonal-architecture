package com.example.hexagonal.application.item;

import com.example.hexagonal.application.exception.NotFoundException;
import com.example.hexagonal.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

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
    void 아이템_저장_성공() {
        // given
        String name = "item";
        Integer price = 10000;
        ItemCommandParams params = new ItemCommandParams(1L, name, price);
        // when
        Item item = itemService.saveItem(params);
        // then
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getPrice()).isEqualTo(price);
    }

    @Test
    @Transactional
    void 아이템_저장_이름_중복_실패() {
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
    void 아이템_하나_조회_성공() {
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
    void 아이템_하나_조회_실패_아이디값_없음() {
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
    void 아이템_수정_성공() {
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
package com.example.hexagonal.application.item;

import com.example.hexagonal.application.exception.NotFoundException;
import com.example.hexagonal.common.MessageKeys;
import com.example.hexagonal.domain.item.Item;
import com.example.hexagonal.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * 원래 Mysql쓰다가 Mongodb로 전환 해버렸다
 * 어 떡 하 지?
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MongoItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    @Override
    public Item saveItem(ItemCommandParams params) {
        Item item = new Item(params.getId(), params.getName(), params.getPrice());
        return itemRepository.save(item);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageKeys.ITEM_NOT_FOUND));
    }

    @Override
    public void updateItem(Long id, ItemCommandParams params) {
        itemRepository.updateItem(id, params);
    }
}

package com.example.hexagonal.application.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
//@Service
@RequiredArgsConstructor
public class MysqlItemServiceImpl
//        implements ItemService
{
//    private final ItemRepository itemRepository;
//    private final MessageSource messageSource;
//
//    @Override
//    public Item saveItem(ItemCommandParams params) {
//        Item item = new Item(params.getName(), params.getPrice());
//        itemRepository.save(item);
//        return item;
//    }
//
//    @Override
//    public Item findItemById(Long id) {
//        return itemRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(
//                        messageSource.getMessage(MessageKeys.ITEM_NOT_FOUND, null, LocaleContextHolder.getLocale())));
//    }
//
//    @Override
//    @Transactional
//    public void updateItem(Long id, ItemCommandParams params) {
//        Item item = findItemById(id);
//        item.changeName(params.getName());
//        item.changePrice(params.getPrice());
//    }
}

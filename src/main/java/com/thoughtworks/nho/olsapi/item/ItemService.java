package com.thoughtworks.nho.olsapi.item;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
class ItemService {
    ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private final ItemRepository itemRepository;

    Item getItem(long id) throws ItemNotFoundException {
        var optionalFoundItem = itemRepository.findById(id);
        if (optionalFoundItem.isPresent()) {
            return optionalFoundItem.get();
        } else {
            throw new ItemNotFoundException();
        }
    }

    Iterable<Item> getItems() {
        return itemRepository.findAll();
    }

    Item createItem(Item item) {
        return itemRepository.save(item);
    }

    Item updateItem(long id, Item item) throws ItemNotFoundException {
        var optionalFoundItem = itemRepository.findById(id);
        if (optionalFoundItem.isPresent()) {
            var foundItem = optionalFoundItem.get();
            foundItem.setName(item.getName());
            return itemRepository.save(foundItem);
        } else {
            throw new ItemNotFoundException();
        }
    }

    void deleteItem(long id) throws ItemNotFoundException {
        try {
            itemRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException();
        }
    }
}

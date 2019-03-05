package com.thoughtworks.nho.olsapi.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    private final ItemService itemService;

    @GetMapping(value = "/items")
    public Iterable<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping(value = "/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        try {
            var foundItem = itemService.getItem(id);
            return new ResponseEntity<>(foundItem, HttpStatus.OK);
        } catch (ItemNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        var createdItem = itemService.createItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping(value = "/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        try {
            var updatedItem = itemService.updateItem(id, item);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/items/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}

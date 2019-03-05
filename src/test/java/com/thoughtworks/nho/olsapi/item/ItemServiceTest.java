package com.thoughtworks.nho.olsapi.item;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ItemServiceTest {
    @Nested
    class getItem {
        @Nested
        class when_an_existing_id_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void returns_the_item() throws ItemNotFoundException {
                var itemService = new ItemService(itemRepository);
                var expectedItem = new Item();
                when(itemRepository.findById(1L)).thenReturn(Optional.of(expectedItem));
                var actualItem = itemService.getItem(1L);
                assertEquals(expectedItem, actualItem);
            }
        }

        @Nested
        class when_an_non_existing_id_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void throws_item_not_found_exception() {
                var givenId = 1L;
                when(itemRepository.findById(givenId)).thenReturn(Optional.empty());
                var itemService = new ItemService(itemRepository);
                assertThrows(ItemNotFoundException.class, () -> itemService.getItem(givenId));
            }
        }
    }

    @Nested
    class getItems {
        @Nested
        class when_requesting_items {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void returns_items() {
                var expectedItems = new ArrayList<Item>();
                when(itemRepository.findAll()).thenReturn(expectedItems);
                var itemService = new ItemService(itemRepository);
                var actualItems = itemService.getItems();
                assertEquals(expectedItems, actualItems);
            }
        }
    }

    @Nested
    class createItem {
        @Nested
        class when_an_item_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void creates_the_item() {
                var givenItem = new Item();
                var createdItem = new Item();
                when(itemRepository.save(givenItem)).thenReturn(createdItem);
                var itemService = new ItemService(itemRepository);
                var actualItem = itemService.createItem(givenItem);
                assertEquals(createdItem, actualItem);
            }
        }
    }

    @Nested
    class updateItem {
        @Nested
        class when_an_existing_item_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void updates_the_item() {
                var givenItem = new Item();
                var updatedItem = new Item();
                when(itemRepository.save(givenItem)).thenReturn(updatedItem);
                var itemService = new ItemService(itemRepository);
                var actualItem = itemService.createItem(givenItem);
                assertEquals(updatedItem, actualItem);
            }
        }

        @Nested
        class when_an_non_existing_item_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void throws_item_not_found_exception() {
                var givenId = 1L;
                when(itemRepository.findById(givenId)).thenReturn(Optional.empty());
                var itemService = new ItemService(itemRepository);
                assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(givenId, new Item()));
            }
        }
    }

    @Nested
    class deleteItem {
        @Nested
        class when_an_existing_item_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void deletes_the_item() {
                var givenId = 1L;
                doNothing().when(itemRepository).deleteById(givenId);
                var itemService = new ItemService(itemRepository);
                assertDoesNotThrow(() -> itemService.deleteItem(givenId));
            }
        }

        @Nested
        class when_an_non_existing_item_is_given {
            @Mock
            private ItemRepository itemRepository;

            @Test
            void throws_item_not_found_exception() {
                var givenId = 1L;
                doThrow(EmptyResultDataAccessException.class).when(itemRepository).deleteById(givenId);
                var itemService = new ItemService(itemRepository);
                assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(givenId));
            }
        }
    }
}

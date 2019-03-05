package com.thoughtworks.nho.olsapi.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    private static ObjectMapper jacksonDataMapper;

    @BeforeAll
    static void initializeJacksonMapper() {
        jacksonDataMapper = new ObjectMapper();
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        jacksonDataMapper.setDateFormat(dateFormat);
    }

    private ArrayList<Item> buildItems() {
        var expectedItem = buildItem();
        var expectedItems = new ArrayList<Item>();
        expectedItems.add(expectedItem);
        return expectedItems;
    }

    private Item buildItem() {
        var expectedItem = new Item();
        expectedItem.setName("iPhone");
        expectedItem.setCreatedAt(new Date());
        expectedItem.setUpdatedAt(new Date());
        return expectedItem;
    }

    @Nested
    class getItems {
        @Nested
        class when_requesting_items {
            @Test
            void responds_items() throws Exception {
                var expectedItems = buildItems();
                var payload = jacksonDataMapper.writeValueAsString(expectedItems);
                when(itemService.getItems()).thenReturn(expectedItems);
                mockMvc.perform(get("/items"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(payload));
            }
        }
    }

    @Nested
    class getItem {
        @Nested
        class when_an_existing_id_is_given {
            @Test
            void responds_the_item() throws ItemNotFoundException, Exception {
                var givenId = 1L;
                var expectedItem = buildItem();
                var payload = jacksonDataMapper.writeValueAsString(expectedItem);
                when(itemService.getItem(givenId)).thenReturn(expectedItem);
                mockMvc.perform(get("/items/{id}", givenId))
                        .andExpect(status().isOk())
                        .andExpect(content().string(payload));
            }
        }

        @Nested
        class when_an_non_existing_id_is_given {
            @Test
            void responds_404() throws ItemNotFoundException, Exception {
                var givenId = 2L;
                when(itemService.getItem(givenId)).thenThrow(ItemNotFoundException.class);
                mockMvc.perform(get("/items/{id}", givenId))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class createItem {
        @Nested
        class when_an_item_is_given {
            @Test
            void creates_the_item() throws Exception {
                var createdItem = buildItem();
                var payload = jacksonDataMapper.writeValueAsString(createdItem);
                when(itemService.createItem(any(Item.class))).thenReturn(createdItem);
                mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(payload));
            }
        }
    }


    @Nested
    class updateItem {
        @Nested
        class when_an_existing_item_is_given {
            @Test
            void updates_the_item() throws Exception, ItemNotFoundException {
                var givenId = 1L;
                var updatedItem = buildItem();
                var payload = jacksonDataMapper.writeValueAsString(updatedItem);
                when(itemService.updateItem(anyLong(), any(Item.class))).thenReturn(updatedItem);
                mockMvc.perform(put("/items/{id}", givenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                        .andExpect(status().isOk())
                        .andExpect(content().string(payload));
            }
        }

        @Nested
        class when_an_non_existing_item_is_given {
            @Test
            @DisplayName("responds 404")
            void responds404() throws ItemNotFoundException, Exception {
                var givenId = 2L;
                var item = buildItem();
                var payload = jacksonDataMapper.writeValueAsString(item);
                when(itemService.updateItem(anyLong(), any(Item.class)))
                        .thenThrow(ItemNotFoundException.class);
                mockMvc.perform(put("/items/{id}", givenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class deleteItem {
        @Nested
        class when_an_existing_item_is_given {
            @Test
            void deletes_the_item() throws ItemNotFoundException, Exception {
                var givenId = 1L;
                doNothing().when(itemService).deleteItem(givenId);
                mockMvc.perform(delete("/items/{id}", givenId))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        class when_an_non_existing_item_is_given {
            @Test
            void responds_404() throws ItemNotFoundException, Exception {
                var givenId = 2L;
                doThrow(ItemNotFoundException.class).when(itemService).deleteItem(givenId);
                mockMvc.perform(delete("/items/{id}", givenId))
                        .andExpect(status().isNotFound());
            }
        }
    }
}

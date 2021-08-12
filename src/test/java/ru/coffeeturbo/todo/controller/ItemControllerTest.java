package ru.coffeeturbo.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.coffeeturbo.todo.model.Item;
import ru.coffeeturbo.todo.service.ItemService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    ItemController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService service;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }


    @Test
    void whenItemCreatesSuccess() throws Exception {
        Item item = createItem(1);

        when(service.add(any(Item.class))).thenReturn(item);

        mockMvc.perform(
                        post("/items").content(asJsonString(item)).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("text description " + 1)))
                .andExpect(jsonPath("$.done", is(false)))
                .andExpect(jsonPath("$.created", notNullValue()));
    }

    @Test
    void whenItemUpdateSuccess() throws Exception {
        Item item = createItem(1);

        when(service.update(any(Item.class))).thenReturn(item);

        mockMvc.perform(
                        put("/items/1")
                                .content(asJsonString(item))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("text description " + 1)))
                .andExpect(jsonPath("$.done", is(false)))
                .andExpect(jsonPath("$.created", notNullValue()));
    }

    @Test
    void whenItemUpdateFailed() throws Exception {
        var item = createItem(99999);

        when(service.update(any(Item.class))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(
                        put("/items/99999")
                                .content(asJsonString(item))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetItemByIdSuccess() throws Exception {
        Item item = createItem(1);

        when(service.findById(any(Long.class))).thenReturn(item);

        mockMvc.perform(get("/items/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("text description " + 1)))
                .andExpect(jsonPath("$.done", is(false)))
                .andExpect(jsonPath("$.created", notNullValue()));
    }

    @Test
    void whenGetItemByIdFailedNotFound() throws Exception {
        when(service.findById(any(Long.class))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/items/" + 99999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testWhenGetAllItems() throws Exception {

        var items = List.of(
                createItem(1),
                createItem(2),
                createItem(3),
                createItem(4)
        );

        when(service.findAll()).thenReturn(items);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)))
                .andExpect(jsonPath("$[0].length()", is(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("text description " + 1)))
                .andExpect(jsonPath("$[0].done", is(false)))
                .andExpect(jsonPath("$[0].created", notNullValue()));
    }

    @Test
    void testWhenGetAllItemsByDoneSuccess() throws Exception {

        var items = List.of(
                createItem(1),
                createItem(2)
        );

        when(service.findByDoneAll(anyBoolean())).thenReturn(items);

        mockMvc.perform(get("/items?done=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].length()", is(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("text description " + 1)))
                .andExpect(jsonPath("$[0].done", is(false)))
                .andExpect(jsonPath("$[0].created", notNullValue()));
    }

    @Test
    void testWhenDeleteItemSuccess() throws Exception {
        when(service.delete(createItem(1))).thenReturn(true);
        mockMvc.perform(delete("/items/" + 1))
                .andExpect(status().isOk());
    }

    @Test
    void testWhenDeleteItemNotFoundFailed() throws Exception {
        when(service.findById(anyLong())).thenThrow(NoSuchElementException.class);
        mockMvc.perform(delete("/items/" + 999))
                .andExpect(status().isNotFound());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Item createItem(int id) {
        return Item.builder()
                .id(id)
                .created(new Date(System.currentTimeMillis()))
                .description("text description " + id)
                .done(false)
                .build();
    }

}
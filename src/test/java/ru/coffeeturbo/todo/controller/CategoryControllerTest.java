package ru.coffeeturbo.todo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.coffeeturbo.todo.model.Category;
import ru.coffeeturbo.todo.service.CategoryService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    CategoryController controller;

    @MockBean
    CategoryService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCategiryControllerExists() {
        assertNotNull(controller);
    }

    @Test
    void whenlistAllSuccess() throws Exception {

        List<Category> categories = List.of(
                new Category(1, "First category"),
                new Category(2, "Second category")
        );

        Mockito.when(service.findAll()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].length()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("First category")))
        ;
    }
}
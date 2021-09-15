package ru.coffeeturbo.todo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.coffeeturbo.todo.model.Category;
import ru.coffeeturbo.todo.service.CategoryService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    private static final String TESTING_URI = "/categories";

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
    @WithMockUser()
    void whenlistAllSuccess() throws Exception {

        List<Category> categories = List.of(
                new Category(1, "First category"),
                new Category(2, "Second category")
        );

        Mockito.when(service.findAll()).thenReturn(categories);

        mockMvc.perform(get(TESTING_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].length()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("First category")))
        ;
    }

    @Test
    void whenlistAllForbidden() throws Exception {
        mockMvc.perform(get(TESTING_URI))
                .andExpect(status().isForbidden())
        ;
    }
}
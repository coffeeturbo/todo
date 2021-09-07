package ru.coffeeturbo.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.coffeeturbo.todo.model.Item;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.service.ItemService;
import ru.coffeeturbo.todo.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    UserController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void whenSignUpSuccess() throws Exception {
        User user = new User(1L, "testEmeil", "testPassword");

        Mockito.when(service.save(any(User.class))).thenReturn(user);

        mockMvc.perform(
                        post("/users/sign-up")
                                .content(asJsonString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findAll() throws Exception {

        var users = List.of(
                new User(1L, "name1@email.ru", "testPasswd1"),
                new User(2L, "name2@email.ru", "testPasswd2"),
                new User(3L, "name3@email.ru", "testPasswd3")
        );
        Mockito.when(service.findAll()).thenReturn(users);

        mockMvc.perform(
                        get("/users/all")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].length()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("name1@email.ru")))
                .andExpect(jsonPath("$[0].password", is("testPasswd1"))
        );
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
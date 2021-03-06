package ru.coffeeturbo.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
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
    @WithMockUser()
    void findAllSuccess() throws Exception {

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
                .andExpect(jsonPath("$[0].length()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("name1@email.ru")))
        ;
    }

    @Test
    void findAllForbidden() throws Exception {
        mockMvc.perform(
                        get("/users/all")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
        ;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
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
import ru.coffeeturbo.todo.controller.dto.AuthenticationRequestDto;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private static final String REGISTER_URI = "/auth/register";
    private static final String LOGIN_URI = "/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    @MockBean
    private UserService service;

    @Test
    public void whenContextLoadsSuccess() {
        assertNotNull(authController);
    }
// todo fix this test
//    @Test
//    void whenLoginSuccess() throws Exception {
//        User user = new User(1L, "testEmeil", "testPassword");
//
//        var dto = new AuthenticationRequestDto(user.getEmail(), user.getPassword());
//        Mockito.when(service.findByEmail(any(String.class))).thenReturn(user);
//        mockMvc.perform(
//                        post(LOGIN_URI)
//                                .content(asJsonString(dto))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk());
//    }

    @Test
    void whenRegisterUserSuccess() throws Exception {
        User user = new User(1L, "testEmeil", "testPassword");

        var dto = new AuthenticationRequestDto(user.getEmail(), user.getPassword());
        Mockito.when(service.register(any(User.class))).thenReturn(user);

        mockMvc.perform(
                        post(REGISTER_URI)
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void whenRegisterUserFailedUserAlreadyExistsForbidden() throws Exception {
        User user = new User(1L, "testEmeil", "testPassword");

        var dto = new AuthenticationRequestDto(user.getEmail(), user.getPassword());
        Mockito.when(service.findByEmail(any(String.class))).thenReturn(user);

        mockMvc.perform(
                        post(REGISTER_URI)
                            .content(asJsonString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
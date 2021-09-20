package ru.coffeeturbo.todo.security.jwt;

import org.junit.jupiter.api.Test;
import ru.coffeeturbo.todo.model.User;

import static org.junit.jupiter.api.Assertions.*;

class JwtUserFactoryTest {

    @Test
    void create() {

        User user = User.builder()
                .id(1L)
                .password("testPassword")
                .email("testEmail")
                .build();

        var jwtUser = JwtUserFactory.create(user);

        assertEquals("testEmail", jwtUser.getUsername());
        assertEquals("testPassword", jwtUser.getPassword());
        assertEquals(1, jwtUser.getId());
        assertTrue(jwtUser.getAuthorities().isEmpty());


    }
}
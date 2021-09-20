package ru.coffeeturbo.todo.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUserTest {
    private JwtUser jwtUser;

    @BeforeEach
    void createUser() {
         jwtUser = new JwtUser(
                1,
                "testEmail@mail.com",
                "testPassword",
                List.of(new SimpleGrantedAuthority("USER_ROLE"))
        );
    }

    @Test
    void getId() {
        assertEquals(1, jwtUser.getId());
    }

    @Test
    void getEmail() {
        assertEquals("testEmail@mail.com", jwtUser.getEmail());
    }

    @Test
    void getAuthorities() {
        assertEquals(1, jwtUser.getAuthorities().size());
    }

    @Test
    void getPassword() {
        assertEquals("testPassword", jwtUser.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("testEmail@mail.com", jwtUser.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(jwtUser.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(jwtUser.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(jwtUser.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(jwtUser.isEnabled());
    }


}
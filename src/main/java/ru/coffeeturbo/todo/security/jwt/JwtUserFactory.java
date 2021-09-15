package ru.coffeeturbo.todo.security.jwt;

import ru.coffeeturbo.todo.model.User;

import java.util.Collections;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}

package ru.coffeeturbo.todo.service;

import ru.coffeeturbo.todo.model.User;

import java.util.List;

public interface UserServiceInterface {
    User register(User user);
    List<User> findAll();
    User findByEmail(String username);
    User findById(long id);
    void delete(long id);
}

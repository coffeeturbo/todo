package ru.coffeeturbo.todo.repository;

import org.springframework.stereotype.Component;
import ru.coffeeturbo.todo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStore {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public void save(User person) {
        users.put(person.getEmail(), person);
    }


    public User findByUsername(String username) {
        return users.get(username);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}

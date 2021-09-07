package ru.coffeeturbo.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.coffeeturbo.todo.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void save() {
        User user = new User(null, "name@email.ru", "testPasswd");
        userService.save(user);
        assertNotEquals(0, user.getId());

    }

    @Test
    void saveAll() {
        var users = List.of(
                new User(null, "name1@email.ru", "testPasswd1"),
                new User(null, "name2@email.ru", "testPasswd2"),
                new User(null, "name3@email.ru", "testPasswd3")
        );

        userService.saveAll(users);
        users.forEach(user -> assertNotEquals(0, user.getId()));
    }

    @Rollback
    @Test
    void findAll() {
        var users = List.of(
                new User(null, "name1@email.ru", "testPasswd1"),
                new User(null, "name2@email.ru", "testPasswd2"),
                new User(null, "name3@email.ru", "testPasswd3")
        );

        userService.saveAll(users);
        var getAllUsers = userService.findAll();
        assertEquals(3, getAllUsers.size());
    }

}
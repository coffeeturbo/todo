package ru.coffeeturbo.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>>  findAll() {
        return  ResponseEntity.ok(userService.findAll());
    }


}

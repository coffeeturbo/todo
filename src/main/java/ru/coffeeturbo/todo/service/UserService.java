package ru.coffeeturbo.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public void saveAll(List<User> usersList) {
        userRepository.saveAll(usersList);
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow();
    }

}

package ru.coffeeturbo.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public void saveAll(List<User> usersList) {
        userRepository.saveAll(usersList);
    }

}

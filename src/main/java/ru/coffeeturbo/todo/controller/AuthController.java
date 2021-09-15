package ru.coffeeturbo.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coffeeturbo.todo.controller.dto.AuthenticationRequestDto;
import ru.coffeeturbo.todo.model.User;
import ru.coffeeturbo.todo.security.jwt.JwtTokenProvider;
import ru.coffeeturbo.todo.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/auth/", "/auth"})
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider provider;
    private final UserService userService;


    @PostMapping({"/login", "/login/"})
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String password = requestDto.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userService.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + email + "not found");
            }

            var token = provider.createToken(email);

            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AuthenticationRequestDto requestDto) {

        if (userService.findByEmail(requestDto.getEmail()) != null) {
            throw new BadCredentialsException("user with email: " + requestDto.getEmail() + "already exists");
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();


        userService.register(user);

        return ResponseEntity.ok(user);
    }
}

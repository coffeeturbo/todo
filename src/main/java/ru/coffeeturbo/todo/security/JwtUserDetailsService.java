package ru.coffeeturbo.todo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.coffeeturbo.todo.security.jwt.JwtUserFactory;
import ru.coffeeturbo.todo.service.UserService;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = service.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return JwtUserFactory.create(user);
    }
}

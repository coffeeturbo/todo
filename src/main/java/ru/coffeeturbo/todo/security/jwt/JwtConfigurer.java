package ru.coffeeturbo.todo.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider provider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtTokenFilter filter = new JwtTokenFilter(provider);
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}

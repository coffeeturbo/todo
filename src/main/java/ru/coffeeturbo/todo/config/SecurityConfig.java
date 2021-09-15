package ru.coffeeturbo.todo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.coffeeturbo.todo.security.jwt.JwtConfigurer;
import ru.coffeeturbo.todo.security.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider provider;

    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String LOGIN_ENDPOINT = "/auth/**";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and().apply(new JwtConfigurer(provider));
    }
}

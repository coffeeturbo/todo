package ru.coffeeturbo.todo.controller.dto;

import lombok.Value;

@Value
public class AuthenticationRequestDto {
    String email;
    String password;
}

package com.elisariane.aluratechcase.domain.user;

public record UserRegistrationData(String name, String username, String email, String password, Role role) {
}

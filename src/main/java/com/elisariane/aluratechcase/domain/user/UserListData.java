package com.elisariane.aluratechcase.domain.user;

public record UserListData(String username, String email, Role role) {

    public UserListData(User user) {
        this(user.getUsername(), user.getEmail(),  user.getRole());
    }
}

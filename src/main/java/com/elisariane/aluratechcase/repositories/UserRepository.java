package com.elisariane.aluratechcase.repositories;

import com.elisariane.aluratechcase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUsername(String username);

    UserDetails findByUsername(String username);

    Optional<User> findByEmail(String email);
}

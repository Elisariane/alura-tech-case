package com.elisariane.aluratechcase.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-z]+$", message = "Username must contain only lowercase letters")
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(UserRegistrationData userRegistrationData, String encryptedPassword) {
        this.name = userRegistrationData.name();
        this.username = userRegistrationData.username();
        this.email = userRegistrationData.email();
        this.password = encryptedPassword;
        this.role = userRegistrationData.role();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

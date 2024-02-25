package com.example.auth.entity;


import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Setter
@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String uuid;
    private String login;
    private String password;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean isLocked;
    private boolean isEnabled;

    public User() {
        generateUuid();
    }

    public User(long id, String uuid, String login, String password, String email, Role role, boolean isLocked, boolean isEnabled) {
        this.id = id;
        this.uuid = uuid;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String generateUuid() {
        return UUID.randomUUID().toString();
    }
}

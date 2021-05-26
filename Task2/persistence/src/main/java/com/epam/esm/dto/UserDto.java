package com.epam.esm.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDto implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private Set<String> roles;

    public UserDto() {

        roles = new HashSet<>();
    }

    public UserDto(Long id) {

        this.id = id;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Set<String> getRoles() {

        return roles;
    }

    public void setRoles(Set<String> roles) {

        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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

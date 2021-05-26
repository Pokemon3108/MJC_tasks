package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User entity
 */
@Table(name = "usr")
@Entity
@Audited
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String name) {

        this.username = name;
    }

    public Set<Order> getOrders() {

        return orders;
    }

    public void setOrders(Set<Order> orders) {

        this.orders = orders;
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

}

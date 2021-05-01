package com.epam.esm.dto;

import java.util.Set;

public class UserDto {

    private Long id;

    private String name;

    private Set<OrderDto> orders;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Set<OrderDto> getOrders() {

        return orders;
    }

    public void setOrders(Set<OrderDto> orders) {

        this.orders = orders;
    }
}

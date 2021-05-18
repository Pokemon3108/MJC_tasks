package com.epam.esm.model;

import org.springframework.hateoas.CollectionModel;

public class PageOrderModel {

    private final CollectionModel<OrderModel> orders;

    private final Page page;

    public PageOrderModel(CollectionModel<OrderModel> orders, Page page) {

        this.orders = orders;
        this.page = page;
    }

    public CollectionModel<OrderModel> getOrders() {

        return orders;
    }

    public Page getPage() {

        return page;
    }
}

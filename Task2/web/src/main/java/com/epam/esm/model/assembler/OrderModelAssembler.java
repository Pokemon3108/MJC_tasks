package com.epam.esm.model.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.converter.GiftCertificateDtoConverter;
import com.epam.esm.entity.Order;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.UserModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {

    private GiftCertificateModelAssembler certificateModelAssembler;

    private GiftCertificateDtoConverter dtoConverter;

    @Autowired
    public OrderModelAssembler(GiftCertificateModelAssembler assembler, GiftCertificateDtoConverter converter) {

        super(OrderController.class, OrderModel.class);

        this.certificateModelAssembler = assembler;
        this.dtoConverter = converter;
    }

    @Override
    public OrderModel toModel(Order entity) {

        OrderModel orderModel = instantiateModel(entity);

        orderModel.add(
                linkTo(methodOn(OrderController.class).create(null, null)).withRel("create")
        );

        orderModel.setCost(entity.getCost());
        orderModel.setId(entity.getId());
        orderModel.setPurchaseDate(entity.getPurchaseDate());
        orderModel.setCertificateDtoModel(
                certificateModelAssembler.toModel(
                        dtoConverter
                                .convertToDto(entity.getCertificate(), entity.getCertificate().getTags())));

        return orderModel;
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities) {

        CollectionModel<OrderModel> orderModels = super.toCollectionModel(entities);
        return orderModels;
    }
}

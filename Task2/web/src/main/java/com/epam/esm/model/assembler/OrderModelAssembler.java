package com.epam.esm.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.OrderModel;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderModel> {

    private GiftCertificateModelAssembler certificateModelAssembler;

    private UserModelAssembler userModelAssembler;

    @Autowired
    public OrderModelAssembler(GiftCertificateModelAssembler certificateAssembler, UserModelAssembler userAssembler) {

        super(OrderController.class, OrderModel.class);

        this.certificateModelAssembler = certificateAssembler;
        this.userModelAssembler = userAssembler;
    }

    @Override
    public OrderModel toModel(OrderDto entity) {

        OrderModel orderModel = instantiateModel(entity);

        orderModel.add(
                linkTo(methodOn(OrderController.class).read(entity.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).create(null, null)).withRel("create")
        );

        orderModel.setCost(entity.getCost());
        orderModel.setId(entity.getId());
        orderModel.setPurchaseDate(entity.getPurchaseDate());
        orderModel.setCertificate(certificateModelAssembler.toModel(entity.getCertificate()));
        orderModel.setUser(userModelAssembler.toModel(entity.getUser()));

        return orderModel;
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends OrderDto> entities) {

        return super.toCollectionModel(entities);
    }
}

package com.epam.esm.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.UserController;
import com.epam.esm.entity.User;
import com.epam.esm.model.UserModel;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

    public UserModelAssembler() {

        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User entity) {

        UserModel userModel = instantiateModel(entity);

        userModel.add(linkTo(methodOn(UserController.class).read(entity.getId())).withSelfRel());

        userModel.setId(entity.getId());
        userModel.setName(entity.getName());
        return userModel;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {

        CollectionModel<UserModel> usersModel = super.toCollectionModel(entities);
        return usersModel;
    }
}

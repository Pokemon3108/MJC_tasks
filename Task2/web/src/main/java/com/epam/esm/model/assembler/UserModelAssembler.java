package com.epam.esm.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.UserModel;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public UserModelAssembler() {

        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {

        UserModel userModel = instantiateModel(entity);

        userModel.add(linkTo(methodOn(UserController.class).read(entity.getId())).withSelfRel());

        userModel.setId(entity.getId());
        userModel.setName(entity.getName());
        return userModel;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends UserDto> entities) {

        CollectionModel<UserModel> usersModel = super.toCollectionModel(entities);
        return usersModel;
    }
}

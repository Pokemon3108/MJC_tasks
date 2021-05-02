package com.epam.esm.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.TagModel;

@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<TagDto, TagModel> {

    public TagModelAssembler() {

        super(TagController.class, TagModel.class);
    }

    @Override
    public TagModel toModel(TagDto entity) {

        TagModel tagModel = instantiateModel(entity);

        TagController controller = methodOn(TagController.class);

        tagModel.add(linkTo(controller.read(entity.getId())).withSelfRel()
                .andAffordance(afford(controller.create(null, null))),
                linkTo(controller.delete(entity.getId())).withRel("delete")
        );

        tagModel.setId(entity.getId());
        tagModel.setName(entity.getName());
        return tagModel;
    }

    @Override
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends TagDto> entities) {

        return super.toCollectionModel(entities);
    }
}

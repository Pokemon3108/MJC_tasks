package com.epam.esm.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificateModel;

@Component
public class GiftCertificateModelAssembler extends
        RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateModel> {

    private TagModelAssembler tagModelAssembler;

    @Autowired
    public GiftCertificateModelAssembler(TagModelAssembler tagModelAssembler) {

        super(CertificateController.class, GiftCertificateModel.class);
        this.tagModelAssembler = tagModelAssembler;
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificateDto entity) {

        GiftCertificateModel giftCertificateModel = instantiateModel(entity);

        CertificateController controller = methodOn(CertificateController.class);

        giftCertificateModel.add(linkTo(controller.read(entity.getId())).withSelfRel()
                        .andAffordance(afford(controller.update(entity.getId(), null)))
                        .andAffordance(afford(controller.create(null, null))),
                linkTo(controller.create(null, null)).withRel("create"),
                linkTo(controller.update(entity.getId(), null)).withRel("update"),
                linkTo(controller.delete(entity.getId())).withRel("delete"),
                linkTo(controller.getCertificates(1, 5, null, null, null, null, null))
                        .withRel("searchAndSort")
        );

        giftCertificateModel.setCreateDate(entity.getCreateDate());
        giftCertificateModel.setDescription(entity.getDescription());
        giftCertificateModel.setDuration(entity.getDuration());
        giftCertificateModel.setId(entity.getId());
        giftCertificateModel.setLastUpdateDate(entity.getLastUpdateDate());
        giftCertificateModel.setName(entity.getName());
        giftCertificateModel.setPrice(entity.getPrice());
        giftCertificateModel.setTags(new HashSet<>(tagModelAssembler
                .toCollectionModel(entity.getTags()).getContent()));

        return giftCertificateModel;
    }

    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificateDto> entities) {

        CollectionModel<GiftCertificateModel> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(CertificateController.class).create(null, null))
                .withRel("create"));
        return collectionModel;
    }
}

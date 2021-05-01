package com.epam.esm.model.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificateModel;

@Component
public class GiftCertificateModelAssembler extends
        RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateModel> {


    public GiftCertificateModelAssembler() {

        super(CertificateController.class, GiftCertificateModel.class);
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificateDto entity) {

        return null;
    }

    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificateDto> entities) {

        return super.toCollectionModel(entities);
    }
}

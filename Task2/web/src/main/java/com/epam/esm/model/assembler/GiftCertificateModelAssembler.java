package com.epam.esm.model.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificateDtoModel;

@Component
public class GiftCertificateModelAssembler extends
        RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateDtoModel> {


    public GiftCertificateModelAssembler() {

        super(CertificateController.class, GiftCertificateDtoModel.class);
    }

    @Override
    public GiftCertificateDtoModel toModel(GiftCertificateDto entity) {

        return null;
    }

    @Override
    public CollectionModel<GiftCertificateDtoModel> toCollectionModel(Iterable<? extends GiftCertificateDto> entities) {

        return super.toCollectionModel(entities);
    }
}

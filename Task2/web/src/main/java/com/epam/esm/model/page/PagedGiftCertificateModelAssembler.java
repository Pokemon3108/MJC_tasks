package com.epam.esm.model.page;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.epam.esm.controller.CertificateController;

@Component
public class PagedGiftCertificateModelAssembler extends
        RepresentationModelAssemblerSupport<PagedCertificateDto, PagedGiftCertificateModel> {

    public PagedGiftCertificateModelAssembler() {

        super(CertificateController.class, PagedGiftCertificateModel.class);
    }

    @Override
    public PagedGiftCertificateModel toModel(PagedCertificateDto entity) {

        PagedGiftCertificateModel pagedModel = instantiateModel(entity);

        pagedModel.setCertificateDtos(entity.getCertificateDtos());
        pagedModel.setPage(entity.getPage());

        return pagedModel;
    }
}

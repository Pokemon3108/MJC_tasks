package com.epam.esm.model;

import org.springframework.hateoas.CollectionModel;

public class PageCertificateModel {

    private final CollectionModel<GiftCertificateModel> certificateModels;

    private final Page page;

    public PageCertificateModel(
            CollectionModel<GiftCertificateModel> certificateModels, Page page) {

        this.certificateModels = certificateModels;
        this.page = page;
    }

    public CollectionModel<GiftCertificateModel> getCertificateModels() {

        return certificateModels;
    }

    public Page getPage() {

        return page;
    }
}

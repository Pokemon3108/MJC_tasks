package com.epam.esm.model.page;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PagedCertificateDto {

    @JsonProperty("certificates")
    private List<GiftCertificateDto> certificateDtos;

    private Page page;

    public List<GiftCertificateDto> getCertificateDtos() {

        return certificateDtos;
    }

    public void setCertificateDtos(List<GiftCertificateDto> certificateDtos) {

        this.certificateDtos = certificateDtos;
    }

    public Page getPage() {

        return page;
    }

    public void setPage(Page page) {

        this.page = page;
    }
}

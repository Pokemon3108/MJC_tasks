package com.epam.esm.dto;

import java.util.Set;

public class TagDto {

    private Long id;

    private String name;

    private Set<GiftCertificateDto> certificates;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Set<GiftCertificateDto> getCertificates() {

        return certificates;
    }

    public void setCertificates(Set<GiftCertificateDto> certificates) {

        this.certificates = certificates;
    }
}

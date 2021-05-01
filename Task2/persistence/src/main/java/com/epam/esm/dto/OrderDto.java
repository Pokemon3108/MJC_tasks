package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {

    private Long id;

    private BigDecimal cost;

    private LocalDateTime purchaseDate;

    private GiftCertificateDto certificate;

    private UserDto user;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public BigDecimal getCost() {

        return cost;
    }

    public void setCost(BigDecimal cost) {

        this.cost = cost;
    }

    public LocalDateTime getPurchaseDate() {

        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {

        this.purchaseDate = purchaseDate;
    }

    public GiftCertificateDto getCertificate() {

        return certificate;
    }

    public void setCertificate(GiftCertificateDto certificate) {

        this.certificate = certificate;
    }

    public UserDto getUser() {

        return user;
    }

    public void setUser(UserDto user) {

        this.user = user;
    }
}



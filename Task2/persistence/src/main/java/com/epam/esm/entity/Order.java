package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Orders contain user's certificates
 */
@Table(name = "ordr")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal cost;

    @Column
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private GiftCertificate certificate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

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

    public GiftCertificate getCertificate() {

        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {

        this.certificate = certificate;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public LocalDateTime getPurchaseDate() {

        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {

        this.purchaseDate = purchaseDate;
    }

    @PrePersist
    public void prePersist() {

        this.purchaseDate = LocalDateTime.now();
    }
}

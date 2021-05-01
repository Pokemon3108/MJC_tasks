package com.epam.esm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("order")
@JsonInclude(Include.NON_NULL)
public class OrderModel extends RepresentationModel<OrderModel> {

    private Long id;

    private BigDecimal cost;

    private LocalDateTime purchaseDate;

    private GiftCertificateDtoModel certificateDtoModel;

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

    public GiftCertificateDtoModel getCertificateDtoModel() {

        return certificateDtoModel;
    }

    public void setCertificateDtoModel(GiftCertificateDtoModel certificateDtoModel) {

        this.certificateDtoModel = certificateDtoModel;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        OrderModel that = (OrderModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(certificateDtoModel, that.certificateDtoModel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, cost, purchaseDate, certificateDtoModel);
    }
}

package com.epam.esm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "order")
@JsonInclude(Include.NON_NULL)
public class OrderModel extends RepresentationModel<OrderModel> {

    private Long id;

    private BigDecimal cost;

    private LocalDateTime purchaseDate;

    private GiftCertificateModel certificateModel;

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

    public GiftCertificateModel getCertificateModel() {

        return certificateModel;
    }

    public void setCertificateModel(GiftCertificateModel certificateModel) {

        this.certificateModel = certificateModel;
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
                Objects.equals(certificateModel, that.certificateModel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, cost, purchaseDate, certificateModel);
    }
}

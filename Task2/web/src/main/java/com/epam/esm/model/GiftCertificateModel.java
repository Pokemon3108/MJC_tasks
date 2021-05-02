package com.epam.esm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "certificate")
@Relation(collectionRelation = "certificates")
@JsonInclude(Include.NON_NULL)
public class GiftCertificateModel extends RepresentationModel<GiftCertificateModel> {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private Set<TagModel> tags = new HashSet<>();

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

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public Integer getDuration() {

        return duration;
    }

    public void setDuration(Integer duration) {

        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {

        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {

        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {

        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {

        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<TagModel> getTags() {

        return tags;
    }

    public void setTags(Set<TagModel> tags) {

        this.tags = tags;
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
        GiftCertificateModel that = (GiftCertificateModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, description, price, duration, createDate, lastUpdateDate, tags);
    }

    @Override
    public String toString() {

        return "GiftCertificateModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }


}

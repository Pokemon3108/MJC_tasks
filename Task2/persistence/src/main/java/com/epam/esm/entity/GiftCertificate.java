package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * The type Gift certificate is an entity of certificate
 */
public class GiftCertificate {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {

        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {

        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Integer getDuration() {

        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(Integer duration) {

        this.duration = duration;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {

        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(LocalDateTime createDate) {

        this.createDate = createDate;
    }

    /**
     * Gets last update date.
     *
     * @return the last update date
     */
    public LocalDateTime getLastUpdateDate() {

        return lastUpdateDate;
    }

    /**
     * Sets last update date.
     *
     * @param lastUpdateDate the last update date
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {

        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
    }
}

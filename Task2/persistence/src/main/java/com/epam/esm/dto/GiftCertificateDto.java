package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * The type Gift certificate dto uses for transferring certificate between application layers
 */
public class GiftCertificateDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private Set<TagDto> tags = new HashSet<>();

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public Set<TagDto> getTags() {

        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(Set<TagDto> tags) {

        this.tags = tags;
    }

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

    /**
     * Add tag.
     *
     * @param tagDto the tag dto
     */
    public void addTag(TagDto tagDto) {

        tags.add(tagDto);
    }

    /**
     * Gets tag.
     *
     * @param index of tag in tag's storage
     * @return the tag of certificate
     */
    public TagDto getTag(int index) {

        return (index < tags.size()) ? new ArrayList<>(tags).get(index) : null;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiftCertificateDto that = (GiftCertificateDto) o;
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

        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, tags);
    }
}

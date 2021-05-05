package com.epam.esm.entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


/**
 * The type Tag is an entity of tag
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.MERGE)
    private Set<GiftCertificate> certificates;

    /**
     * Instantiates a new Tag.
     */
    public Tag() {

    }

    /**
     * Instantiates a new Tag.
     *
     * @param name the name
     */
    public Tag(String name) {

        this.name = name;
    }

    /**
     * Instantiates a new Tag.
     *
     * @param name the name
     * @param id   the id
     */
    public Tag(String name, Long id) {

        this.name = name;
        this.id = id;
    }

    public Set<GiftCertificate> getCertificates() {

        return certificates;
    }

    public void setCertificates(Set<GiftCertificate> certificates) {

        this.certificates = certificates;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}

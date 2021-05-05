package com.epam.esm.dto;

import java.util.Objects;

public class TagDto {

    private Long id;

    private String name;

    public TagDto() {

    }

    public TagDto(String name) {

        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TagDto tagDto = (TagDto) o;
        return Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}

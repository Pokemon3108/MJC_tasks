package com.epam.esm.dto;

import java.util.List;

public class SortParamsDto {

    final List<String> sortParams;

    final Direction direction;

    public SortParamsDto(List<String> sortParams, Direction direction) {

        this.sortParams = sortParams;
        this.direction = direction;
    }

    public List<String> getSortParams() {

        return sortParams;
    }

    public Direction getDirection() {

        return direction;
    }
}

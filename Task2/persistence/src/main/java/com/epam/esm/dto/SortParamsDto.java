package com.epam.esm.dto;

import java.util.List;

public class SortParamsDto {

    final List<String> sortParams;

    final SortDirection sortDirection;

    public SortParamsDto(List<String> sortParams, SortDirection sortDirection) {

        this.sortParams = sortParams;
        this.sortDirection = sortDirection;
    }

    public List<String> getSortParams() {

        return sortParams;
    }

    public SortDirection getSortDirection() {

        return sortDirection;
    }
}

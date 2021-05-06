package com.epam.esm.model.page;

public class Page {

    private final int size;

    private final int totalElements;

    private final int totalPages;

    private final int number;

    public Page(int size, int totalElements, int totalPages, int number) {

        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    public int getSize() {

        return size;
    }

    public int getTotalElements() {

        return totalElements;
    }

    public int getTotalPages() {

        return totalPages;
    }

    public int getNumber() {

        return number;
    }
}

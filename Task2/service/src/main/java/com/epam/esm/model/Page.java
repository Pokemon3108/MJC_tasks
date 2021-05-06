package com.epam.esm.model;

public class Page {

    private final int size;

    private final long totalElements;

    private final int totalPages;

    private final int number;

    public Page(int size, long totalElements, int totalPages, int number) {

        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    public int getSize() {

        return size;
    }

    public long getTotalElements() {

        return totalElements;
    }

    public int getTotalPages() {

        return totalPages;
    }

    public int getNumber() {

        return number;
    }
}

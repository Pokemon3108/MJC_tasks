package com.epam.esm.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Page page = (Page) o;
        return size == page.size &&
                totalElements == page.totalElements &&
                totalPages == page.totalPages &&
                number == page.number;
    }

    @Override
    public int hashCode() {

        return Objects.hash(size, totalElements, totalPages, number);
    }

    @Override
    public String toString() {

        return "Page{" +
                "size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", number=" + number +
                '}';
    }
}

package com.epam.esm.dto;


/**
 * The enum Direction uses for sorting direction
 */
public enum SortDirection {

    /**
     * Ascending order
     */
    ASC,
    /**
     * Descending order
     */
    DESC;

    public static boolean contains(String direction) {

        for (SortDirection dir : SortDirection.values()) {
            if (dir.name().equals(direction)) {
                return true;
            }
        }

        return false;
    }
}

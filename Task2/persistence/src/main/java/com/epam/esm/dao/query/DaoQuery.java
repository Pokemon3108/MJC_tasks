package com.epam.esm.dao.query;

import java.util.function.Consumer;

/**
 * Utils class for sql queries
 */
public class DaoQuery {

    /**
     * {@code consumer} applies if it's not null
     * @param nullableValue param of {@code consumer}
     * @param consumer function to be executed
     * @param <T> generic type of {@code consumer}
     */
    public static <T> void applyIfNotNull(T nullableValue, Consumer<T> consumer) {

        if (nullableValue != null) {
            consumer.accept(nullableValue);
        }
    }

}

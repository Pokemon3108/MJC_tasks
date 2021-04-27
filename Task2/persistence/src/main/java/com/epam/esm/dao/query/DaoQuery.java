package com.epam.esm.dao.query;

import java.util.function.Consumer;

public class DaoQuery {

    public static <T> void applyIfNotNull(T nullableValue, Consumer<T> consumer) {

        if (nullableValue != null) {
            consumer.accept(nullableValue);
        }
    }

}

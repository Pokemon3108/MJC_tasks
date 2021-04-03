package com.epam.esm.dao;

import java.util.List;

public interface Dao<T> {
    Long insert(T obj);

    void update(long id);

    void delete(T obj);

    T read(long id);

    List<T> findAll();
}

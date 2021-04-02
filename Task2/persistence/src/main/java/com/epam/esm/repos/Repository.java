package com.epam.esm.repos;

import java.util.List;

public interface Repository<T> {
    T read(long id);

    void add(T obj);

    void update(T obj);

    void remove(T obj);

    List<T> findAll();
}

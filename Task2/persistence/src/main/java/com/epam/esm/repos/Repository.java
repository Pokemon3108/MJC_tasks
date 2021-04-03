package com.epam.esm.repos;

import java.util.List;

public interface Repository<T> {
    T read(long id);

    long add(T obj);

    void update(long id);

    void remove(T obj);

    List<T> findAll();
}

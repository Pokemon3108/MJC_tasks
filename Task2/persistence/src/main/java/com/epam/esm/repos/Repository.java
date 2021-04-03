package com.epam.esm.repos;

import java.util.List;

public interface Repository<T> {
    T read(Long id);

    Long add(T obj);

    void update(Long id);

    void remove(T obj);

    List<T> findAll();
}

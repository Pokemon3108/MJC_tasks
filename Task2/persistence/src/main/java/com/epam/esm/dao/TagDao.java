package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public class TagDao implements Dao<Tag> {

    @Override
    public long create(Tag obj) {
        return 0;
    }

    @Override
    public void update(long id) {

    }

    @Override
    public void delete(Tag obj) {

    }

    @Override
    public Tag read(long id) {
        return null;
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }
}

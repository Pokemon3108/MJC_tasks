package com.epam.esm.repos;

import com.epam.esm.entity.Tag;

import java.util.List;

public class TagRepository implements Repository<Tag> {

    @Override
    public Tag read(long id) {
        return null;
    }

    @Override
    public long add(Tag obj) {
        return 0;
    }

    @Override
    public void update(long id) {

    }

    @Override
    public void remove(Tag obj) {

    }

    @Override
    public List<Tag> findAll() {
        return null;
    }
}

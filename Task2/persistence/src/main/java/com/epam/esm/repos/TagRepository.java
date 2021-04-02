package com.epam.esm.repos;

import com.epam.esm.entity.Tag;
import com.epam.esm.repos.exception.IllegalOperationException;

import java.util.List;

public class TagRepository implements Repository<Tag> {
    @Override
    public Tag read(long id) {
        return null;
    }

    @Override
    public void add(Tag obj) {

    }

    @Override
    public void update(Tag obj) {
        throw new IllegalOperationException("Tags cannot be updated");
    }

    @Override
    public void remove(Tag obj) {

    }

    @Override
    public List<Tag> findAll() {
        return null;
    }
}

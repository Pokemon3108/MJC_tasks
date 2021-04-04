package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;


public interface TagDao {
    Long insert(Tag tag);

    void update(long id);

    void delete(Tag tag);

    Tag read(long id);

    List<Tag> findAll();

    Tag findTagByName(String name);
}

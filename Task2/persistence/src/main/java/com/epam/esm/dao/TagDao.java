package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;


public interface TagDao {
    Long insert(Tag tag);

    Tag read(long id);

    void delete(long id);

    List<Tag> findAll();

    Tag readTagByName(String name);

    void deleteCertificateTagsByTagId(long tagId);
}

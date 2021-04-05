package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;

public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    public Tag findTagByName(Tag tag) {
        return tagDao.findTagByName(tag.getName());
    }

    public Long insert(Tag tag) {
        return tagDao.insert(tag);
    }

    @Override
    public Tag findTagById(long id) {
        return tagDao.read(id);
    }
}

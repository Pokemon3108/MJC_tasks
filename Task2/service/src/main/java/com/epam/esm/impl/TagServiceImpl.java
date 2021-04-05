package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of tag service
 */
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag findTagByName(Tag tag) {
        return tagDao.findTagByName(tag.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long insert(Tag tag) {
        return tagDao.insert(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag findTagById(long id) {
        return tagDao.read(id);
    }
}

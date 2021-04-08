package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.TagService;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoTagException;

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
    public Long create(Tag tag) {

        return tagDao.insert(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag readTagById(long id) {

        Tag tag = tagDao.read(id);
        if (tag == null) {
            throw new NoTagException(id);
        }
        return tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag readTagByName(String name) {

        return tagDao.readTagByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        Tag tag = tagDao.read(id);
        if (tag == null) {
            throw new NoTagException(id);
        }
        tagDao.deleteCertificateTagsByTagId(id);
        tagDao.delete(id);
    }
}

package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.TagService;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;

/**
 * Implementation of tag service
 */
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    @Autowired
    public void setTagDao(TagDao tagDao) {

        this.tagDao = tagDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create(Tag tag) {

        if (tagDao.readTagByName(tag.getName()).isPresent()) {
            throw new DuplicateTagException(tag.getName());
        }
        return tagDao.insert(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag readTagById(long id) {

        return tagDao.read(id).orElseThrow(() -> new NoTagException(id));
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        if (!tagDao.read(id).isPresent()) {
            throw new NoTagException(id);
        }
        tagDao.deleteCertificateTagsByTagId(id);
        tagDao.delete(id);
    }
}

package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of tag service
 */
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private GiftCertificateDao certificateDao;

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
        tagDao.deleteCertificateTagsByTagId(id);
        tagDao.delete(id);
    }
}

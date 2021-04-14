package com.epam.esm.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> setTagsId(Set<Tag> tags) {

        Set<Tag> tagsWithId = tagDao
                .readTagsByNames(tags.stream().map(Tag::getName).collect(Collectors.toSet()));
        Map<Boolean, List<Tag>> tagMap = tags.stream()
                .collect(Collectors.partitioningBy(tagsWithId::contains));
        Set<Tag> newTags = new HashSet<>(tagMap.get(false));
        insertTagsIfNotExist(newTags);
        tagsWithId.addAll(newTags);
        return tagsWithId;
    }

    /**
     * Insert tags to storage if they not already exists
     *
     * @param tags with names, id from which will be set
     */
    private void insertTagsIfNotExist(Set<Tag> tags) {

        tags.stream().filter(tag -> tag.getId() == null)
                .forEach(tag -> tag.setId(tagDao.insert(tag)));
    }

}

package com.epam.esm.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.TagService;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.tag.DuplicateTagException;
import com.epam.esm.exception.tag.NoTagException;
import com.epam.esm.exception.user.UsersOrderHasNoTags;

/**
 * Implementation of tag service
 */
@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {

        this.tagDao = tagDao;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public TagDto create(TagDto tag) {

        if (tagDao.readTagByName(tag.getName()).isPresent()) {
            throw new DuplicateTagException(tag.getName());
        }
        return readTagById(tagDao.insert(tag));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDto readTagById(long id) {

        return tagDao.read(id).orElseThrow(() -> new NoTagException(id));
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(long id) {

        Optional<TagDto> tag = tagDao.read(id);
        if (!tag.isPresent()) {
            throw new NoTagException(id);
        }
        tagDao.delete(tag.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDto readMostPopularTag() {

        return tagDao.readTheMostPopularTagOfRichestUser().orElseThrow(UsersOrderHasNoTags::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TagDto> bindTagsWithIds(Set<TagDto> tags) {

        Set<TagDto> tagsWithId = tagDao
                .readTagsByNames(tags.stream().map(TagDto::getName).collect(Collectors.toSet()));
        Map<Boolean, List<TagDto>> tagMap = tags.stream()
                .collect(Collectors.partitioningBy(tagsWithId::contains));
        Set<TagDto> newTags = new HashSet<>(tagMap.get(false));
        tagsWithId.addAll(newTags);
        return tagsWithId;
    }
}

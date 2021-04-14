package com.epam.esm;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.epam.esm.entity.Tag;

/**
 * The interface Tag service for CRD operation
 */
@Service
public interface TagService {

    /**
     * Insert tag into storage
     *
     * @param tag that will be saved
     * @return the tag's id
     */
    Long create(Tag tag);

    /**
     * Find tag in storage by id
     *
     * @param id of tag
     * @return the tag from storage
     */
    Tag readTagById(long id);

    /**
     * Delete tag from storage
     *
     * @param id of tag
     */
    void delete(long id);

    /**
     * Set id for named tags
     * @param tags
     * @return
     */
    Set<Tag> setTagsId(Set<Tag> tags);

}

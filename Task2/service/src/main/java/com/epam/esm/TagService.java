package com.epam.esm;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Service;

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
    Long insert(Tag tag);

    /**
     * Find tag by id
     *
     * @param id of tag
     * @return the tag from storage
     */
    Tag readTagById(long id);

    /**
     * Find tag by its name.
     *
     * @param name of tag
     * @return the tag from storage
     */
    Tag readTagByName(String name);
}

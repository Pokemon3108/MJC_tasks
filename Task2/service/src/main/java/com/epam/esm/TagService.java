package com.epam.esm;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;

/**
 * The interface Tag service for CRD operation
 */
@Service
public interface TagService {

    /**
     * Insert tag into storage
     *
     * @param tag that will be saved
     * @return created tag
     */
    TagDto create(TagDto tag);

    /**
     * Find tag in storage by id
     *
     * @param id of tag
     * @return the tag from storage
     */
    TagDto readTagById(long id);

    /**
     * Delete tag from storage
     *
     * @param id of tag
     */
    void delete(long id);

    /**
     * Set id for named tags
     *
     * @param tags - tags with names
     * @return filled tags
     */
    Set<TagDto> bindTagsWithIds(Set<TagDto> tags);

    /**
     * Read the most popular tag in orders of user, that made orders with the highest price
     *
     * @return the most popular tag
     */
    TagDto readMostPopularTag();

}

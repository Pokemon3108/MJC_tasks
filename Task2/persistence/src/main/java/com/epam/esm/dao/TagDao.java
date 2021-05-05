package com.epam.esm.dao;

import java.util.Optional;
import java.util.Set;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;


/**
 * Interface for work with storage for tags.
 */

public interface TagDao {

    /**
     * Insert tag.
     *
     * @param tag that will be inserted
     * @return the id of tag in storage
     */
    Long insert(TagDto tag);

    /**
     * Read tag by id.
     *
     * @param id
     * @return {@code Optional} mapper of tag dto , or {@code Optional.empty()} if it is not found
     */
    Optional<TagDto> read(long id);

    /**
     * Delete tag from storage
     *
     * @param tag to be deleted
     */
    void delete(TagDto tag);

    /**
     * Read tag by name.
     *
     * @param name the name
     * @return tag from storage, or {@code Optional.empty()} if it is not found
     */
    Optional<TagDto> readTagByName(String name);

    /**
     * Read tags by their names
     *
     * @param tagNames
     * @return the set of filled tags
     */
    Set<TagDto> readTagsByNames(Set<String> tagNames);

    /**
     * Read the most popular tag in user's order
     *
     * @param user - the user with order
     * @return tag from storage, or {@code Optional.empty()} if it is not found
     */
    Optional<TagDto> readTheMostPopularTag(UserDto user);
}

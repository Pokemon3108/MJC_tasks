package com.epam.esm.dao;

import java.util.Optional;
import java.util.Set;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;


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
    Long insert(Tag tag);

    /**
     * Read tag by id.
     *
     * @param id
     * @return tag from storage, or {@code Optional.empty()} if it is not found
     */
    Optional<Tag> read(long id);

    /**
     * Delete tag from storage
     *
     * @param tag to be deleted
     */
    void delete(Tag tag);

    /**
     * Read tag by name.
     *
     * @param name the name
     * @return tag from storage, or {@code Optional.empty()} if it is not found
     */
    Optional<Tag> readTagByName(String name);

    /**
     * Delete tags with id  {@code tagId}  from certificates
     *
     * @param tagId
     */
    void deleteCertificateTagsByTagId(long tagId);

    /**
     * Bind certificate tags with certificate
     *
     * @param tagSet        certificate tags
     * @param certificateId the id of certificate
     */
    void bindCertificateTags(Set<Tag> tagSet, Long certificateId);

    /**
     * Get certificate tags ids
     *
     * @param certificateId the id of certificate
     * @return certificate tags
     */
    Set<Long> readCertificateTagsIdsByCertificateId(long certificateId);

    /**
     * Unbind all certificate tags.
     *
     * @param certificate, that tags will be unbinded
     */
    void unbindCertificateTags(GiftCertificate certificate);

    /**
     * Read tags by their names
     *
     * @param tagNames
     * @return the set of filled tags
     */
    Set<Tag> readTagsByNames(Set<String> tagNames);

    /**
     * Read tags by their ids
     *
     * @param ids of tags
     * @return the set of filled tags
     */
    Set<Tag> readTagsByIds(Set<Long> ids);

    /**
     * Read the most popular tag in user's order
     *
     * @param user - the user with order
     * @return tag from storage, or {@code Optional.empty()} if it is not found
     */
    Optional<Tag> readTheMostPopularTag(User user);
}

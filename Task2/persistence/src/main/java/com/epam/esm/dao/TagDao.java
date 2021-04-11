package com.epam.esm.dao;

import java.util.Optional;
import java.util.Set;

import com.epam.esm.entity.Tag;


public interface TagDao {

    Long insert(Tag tag);

    Optional<Tag> read(long id);

    void delete(long id);

    Optional<Tag> readTagByName(String name);

    void deleteCertificateTagsByTagId(long tagId);

    void bindCertificateTags(Set<Tag> tagSet, Long certificateId);

    Set<Long> readCertificateTagsIdsByCertificateId(long certificateId);

    void unbindCertificateTags(long certificateId);

    Set<Tag> readTagsByNames(Set<String> tagNames);

    Set<Tag> readTagsByIds(Set<Long> ids);
}

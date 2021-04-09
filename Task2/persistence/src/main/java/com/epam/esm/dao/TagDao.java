package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.Tag;


public interface TagDao {

    Long insert(Tag tag);

    Tag read(long id);

    void delete(long id);

    List<Tag> findAll();

    Tag readTagByName(String name);

    void deleteCertificateTagsByTagId(long tagId);

    void insertCertificateTags(GiftCertificateDto giftCertificateDto);

    List<Tag> readCertificateTagsIdByCertificateId(long certificateId);

    void deleteCertificateTagsByCertificateId(long certificateId);
}

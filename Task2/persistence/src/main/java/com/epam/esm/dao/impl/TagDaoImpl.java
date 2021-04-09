package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.Tag;


public class TagDaoImpl implements TagDao {

    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";

    private static final String READ_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";

    private static final String READ_TAG_BY_ID = "SELECT id, name FROM tag WHERE id=?";

    private static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id=?";

    private static final String DELETE_CERTIFICATE_TAGS_BY_TAG_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";

    private static final String INSERT_CERTIFICATE_TAGS = "INSERT INTO gift_certificate_tag" +
            "(certificate_id, tag_id) VALUES(?, ?)";

    private static final String READ_CERTIFICATE_TAGS_ID_BY_CERTIFICATE_ID = "SELECT tag_id " +
            "FROM gift_certificate_tag WHERE certificate_id=?";

    private static final String DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag "
            + "WHERE certificate_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(Tag tag) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_TAG, new String[]{"id"});
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(DELETE_TAG_BY_ID, id);
    }

    @Override
    public Tag read(long id) {

        try {
            return jdbcTemplate
                    .queryForObject(READ_TAG_BY_ID, new Object[]{id}, new int[]{Types.INTEGER}, new TagMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Tag> findAll() {

        return null;
    }

    @Override
    public Tag readTagByName(String name) {

        try {
            return jdbcTemplate
                    .queryForObject(READ_TAG_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, new TagMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new Tag(name);
        }
    }

    @Override
    public void deleteCertificateTagsByTagId(long tagId) {

        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_TAG_ID, tagId);
    }

    @Override
    public void insertCertificateTags(GiftCertificateDto certificate) {

        certificate.getTags().forEach(tag -> jdbcTemplate
                .update(INSERT_CERTIFICATE_TAGS, certificate.getId(), tag.getId()));
    }

    @Override
    public List<Tag> readCertificateTagsIdByCertificateId(long certificateId) {

        try {
            return jdbcTemplate.query(READ_CERTIFICATE_TAGS_ID_BY_CERTIFICATE_ID, new Object[]{certificateId},
                    new int[]{Types.INTEGER}, new TagMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteCertificateTagsByCertificateId(long certificateId) {

        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID, certificateId);
    }
}

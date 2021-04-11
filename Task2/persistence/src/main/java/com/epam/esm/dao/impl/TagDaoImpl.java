package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
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

    private static final String READ_TAGS_BY_TAGS_NAMES = "SELECT id, name FROM tag WHERE name IN(%s)";

    private static final String READ_TAGS_BY_TAGS_IDS = "SELECT id, name FROM tag WHERE id IN(%s)";


    private JdbcTemplate jdbcTemplate;

    private TagMapper tagMapper;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {

        this.tagMapper = tagMapper;
    }

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
    public Optional<Tag> read(long id) {

        List<Tag> tagList = jdbcTemplate.query(READ_TAG_BY_ID, new Object[]{id}, new int[]{Types.INTEGER}, tagMapper);
        return Optional.ofNullable(tagList.get(0));
    }

    @Override
    public Optional<Tag> readTagByName(String name) {

        List<Tag> tagList = jdbcTemplate
                .query(READ_TAG_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, tagMapper);
        return Optional.ofNullable(tagList.get(0));
//        try {
//            return jdbcTemplate
//                    .queryForObject(READ_TAG_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, new TagMapper());
//        } catch (EmptyResultDataAccessException ex) {
//            return new Tag(name);
//        }
    }

    @Override
    public void deleteCertificateTagsByTagId(long tagId) {

        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_TAG_ID, tagId);
    }

    @Override
    public void bindCertificateTags(Set<Tag> tagSet, Long certificateId) {

        tagSet.forEach(tag -> jdbcTemplate
                .update(INSERT_CERTIFICATE_TAGS, certificateId, tag.getId()));
    }

    @Override
    public Set<Long> readCertificateTagsIdsByCertificateId(long certificateId) {

        try {
            return new HashSet<>(
                    jdbcTemplate.queryForList(READ_CERTIFICATE_TAGS_ID_BY_CERTIFICATE_ID, new Object[]{certificateId},
                            new int[]{Types.INTEGER}, Long.class));
        } catch (EmptyResultDataAccessException ex) {
            return new HashSet<>();
        }
    }

    @Override
    public void unbindCertificateTags(long certificateId) {

        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID, certificateId);
    }

    @Override
    public Set<Tag> readTagsByNames(Set<String> tagNames) {

        String inSql = String.join(",", Collections.nCopies(tagNames.size(), "?"));

        List<Tag> tags = jdbcTemplate.query(String.format(READ_TAGS_BY_TAGS_NAMES, inSql),
                tagMapper, tagNames.toArray());

        return new HashSet<>(tags);
    }

    @Override
    public Set<Tag> readTagsByIds(Set<Long> ids) {

        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        List<Tag> tags = jdbcTemplate.query(String.format(READ_TAGS_BY_TAGS_IDS, inSql),
                tagMapper, ids.toArray());

        return new HashSet<>(tags);
    }
}

package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;


public class TagDaoImpl implements TagDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String READ_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
    private static final String READ_TAG_BY_ID = "SELECT id, name FROM tag WHERE id=?";
    private static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id=?";
    private static final String DELETE_CERTIFICATE_TAGS_BY_TAG_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";

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
            return jdbcTemplate.queryForObject(READ_TAG_BY_ID, new Object[]{id}, new int[]{Types.INTEGER}, new TagMapper());
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
            return jdbcTemplate.queryForObject(READ_TAG_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, new TagMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new Tag(name);
        }
    }

    @Override
    public void deleteCertificateTagsByTagId(long tagId) {
        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_TAG_ID, tagId);
    }
}

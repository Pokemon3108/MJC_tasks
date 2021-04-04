package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
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
    private static final String FIND_TAG_BY_NAME = "SELECT (id) FROM tag WHERE name=?";

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
    public void update(long id) {

    }

    @Override
    public void delete(Tag tag) {

    }

    @Override
    public Tag read(long id) {
        return null;
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Tag findTagByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_TAG_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, new TagMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new Tag(name);
        }
    }
}

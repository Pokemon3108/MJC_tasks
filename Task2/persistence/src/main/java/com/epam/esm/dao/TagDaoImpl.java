package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;

public class TagDaoImpl implements Dao<Tag> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_TAG="INSERT INTO tag (name) VALUES (?)";

    @Override
    public Long insert(Tag obj) {
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps=con.prepareStatement(INSERT_TAG);
            ps.setString(1, obj.getName());
            return ps;
        }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    @Override
    public void update(long id) {

    }

    @Override
    public void delete(Tag obj) {

    }

    @Override
    public Tag read(long id) {
        return null;
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }
}

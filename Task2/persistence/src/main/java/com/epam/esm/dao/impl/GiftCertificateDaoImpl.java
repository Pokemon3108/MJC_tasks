package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_CERTIFICATE_TAGS = "INSERT INTO gift_certificate_tag" +
            "(certificate_id, tag_id) VALUES(?, ?)";

    private static final String READ_CERTIFICATE_BY_ID = "SELECT id, name, description, price, duration, create_date," +
            " last_update_date FROM gift_certificate WHERE id=?";

    private static final String READ_CERTIFICATE_TAGS_ID_BY_CERTIFICATE_ID = "SELECT tag_id FROM gift_certificate_tag " +
            "WHERE certificate_id=?";

    @Override
    public Long insert(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_CERTIFICATE, new String[]{"id"});
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setBigDecimal(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(long id) {

    }

    @Override
    public void delete(GiftCertificate certificate) {

    }

    @Override
    public GiftCertificate read(long id) {
        try {
            return jdbcTemplate.queryForObject(READ_CERTIFICATE_BY_ID, new Object[]{id}, new int[]{Types.INTEGER}, new GiftCertificateMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return null;
    }

    @Override
    public void insertCertificateTags(GiftCertificate certificate) {
        certificate.getTags().forEach(tag -> jdbcTemplate
                .update(INSERT_CERTIFICATE_TAGS, certificate.getId(), tag.getId()));
    }

    @Override
    public List<Long> readCertificateTagsIdByCertificateId(long certificateId) {
        try {
            return jdbcTemplate.queryForList(READ_CERTIFICATE_TAGS_ID_BY_CERTIFICATE_ID, new Object[]{certificateId},
                    new int[]{Types.INTEGER}, Long.class);
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }


}

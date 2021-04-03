package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_CERTIFICATE="INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_CERTIFICATE_TAGS="INSERT INTO gift_certificate_tag" +
            "(certificate_id, tag_id) VALUES(?, ?)";

    @Override
    public Long insert(GiftCertificate obj) {
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps=con.prepareStatement(INSERT_CERTIFICATE);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setInt(3, obj.getPrice());
            ps.setInt(4, obj.getDuration());
            ps.setDate(5, Date.valueOf(obj.getCreateDate()));
            ps.setDate(6, Date.valueOf(obj.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    @Override
    public void update(long id) {

    }

    @Override
    public void delete(GiftCertificate obj) {

    }

    @Override
    public GiftCertificate read(long id) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return null;
    }

    @Override
    public void insertCertificateTag(GiftCertificate certificate) {
        certificate.getTags().forEach(tag -> jdbcTemplate
                .update(INSERT_CERTIFICATE_TAGS, certificate.getId(), tag.getId()));
    }
}

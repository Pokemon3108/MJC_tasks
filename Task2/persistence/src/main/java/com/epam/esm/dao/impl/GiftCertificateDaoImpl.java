package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateExtractor;
import com.epam.esm.entity.GiftCertificate;


public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String READ_CERTIFICATE_BY_ID =
            "SELECT cert.name AS cert_name, cert.description AS description , " +
                    "cert.duration AS duration , cert.price AS price , cert.id AS cert_id, cert.name, " +
                    "cert.create_date as create_date, cert.last_update_date as last_update_date, " +
                    "tag.name AS tag_name, tag.id  AS tag_id FROM gift_certificate AS cert " +
                    "LEFT OUTER JOIN gift_certificate_tag AS  cert_tag ON cert.id = cert_tag.certificate_id " +
                    "LEFT OUTER JOIN tag ON tag.id = cert_tag.tag_id WHERE cert.id = ?";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name=COALESCE(?, name)," +
            "description=COALESCE(?, description), price=COALESCE(?, price), " +
            "duration=COALESCE(?, duration), last_update_date=? WHERE id=?";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    //TODO to tag dao
    private static final String INSERT_CERTIFICATE_TAGS = "INSERT INTO gift_certificate_tag" +
            "(certificate_id, tag_id) VALUES(?, ?)";
    private static final String READ_CERTIFICATE_TAGS_ID_BY_CERTIFICATE_ID = "SELECT tag_id " +
            "FROM gift_certificate_tag WHERE certificate_id=?";
    private static final String DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE certificate_id=?";
    private static final String READ_CERTIFICATE_BY_PARAMS = "SELECT cert.name AS cert_name, " +
            "cert.description AS description, " +
            "cert.duration AS duration , cert.price AS price , cert.id AS cert_id, " +
            "cert.create_date as create_date, cert.last_update_date as last_update_date, " +
            "tag.name AS tag_name, tag.id  AS tag_id FROM gift_certificate AS cert " +
            "LEFT JOIN gift_certificate_tag AS  cert_tag ON cert.id = cert_tag.certificate_id " +
            "LEFT JOIN tag ON tag.id = cert_tag.tag_id " +
            "WHERE cert.id " +
            "IN (SELECT gc.id FROM gift_certificate gc LEFT JOIN gift_certificate_tag gct ON gc.id=gct.certificate_id "
            +
            "LEFT JOIN tag ON  gct.tag_id = tag.id WHERE tag.name=COALESCE(?, tag.name)) " +
            "AND cert.name LIKE CONCAT('%', COALESCE(?, cert.name), '%') " +
            "AND cert.description=COALESCE(?, cert.description)";
    private static final String READ_ALL = "SELECT cert.name AS cert_name, cert.description AS description ," +
            " cert.duration AS duration , cert.price AS price , cert.id AS cert_id, cert.name, " +
            " cert.create_date as create_date, cert.last_update_date as last_update_date," +
            " tag.name AS tag_name, tag.id  AS tag_id FROM gift_certificate AS cert" +
            "  LEFT OUTER JOIN gift_certificate_tag AS  cert_tag ON cert.id = cert_tag.certificate_id" +
            " LEFT OUTER JOIN tag ON tag.id = cert_tag.tag_id ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public void update(GiftCertificate certificate) {

        jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(),
                certificate.getLastUpdateDate(), certificate.getId());
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public GiftCertificate read(long id) {

        List<GiftCertificate> certificates = jdbcTemplate.query(READ_CERTIFICATE_BY_ID, new Object[]{id},
                new int[]{Types.INTEGER}, new GiftCertificateExtractor());
        if (certificates == null || certificates.isEmpty()) {
            return null;
        } else {
            return certificates.get(0);
        }

    }

    @Override
    public List<GiftCertificate> findAll() {

        return jdbcTemplate.query(READ_ALL, new GiftCertificateExtractor());
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

    @Override
    public void deleteCertificateTagsByCertificateId(long certificateId) {

        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID, certificateId);
    }


    @Override
    public List<GiftCertificate> findCertificateByParams(GiftCertificate certificate) {

        return jdbcTemplate.query(READ_CERTIFICATE_BY_PARAMS, ps -> {
            if (certificate.hasTags()) {
                ps.setString(1, certificate.getTag(0).getName());
            } else {
                ps.setString(1, null);
            }
            ps.setString(2, certificate.getName());
            ps.setString(3, certificate.getDescription());
        }, new GiftCertificateExtractor());

    }


}

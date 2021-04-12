package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateDtoConverter;
import com.epam.esm.entity.GiftCertificate;


public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String READ_CERTIFICATE_BY_ID =
            "SELECT name , description , duration ,  price , id , create_date, last_update_date "
                    + "FROM gift_certificate WHERE id = ?";

    private static final String READ_CERTIFICATE_BY_NAME =
            "SELECT name , description , duration ,  price , id , create_date, last_update_date "
                    + "FROM gift_certificate WHERE name = ?";

    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = COALESCE(?, name)," +
            "description=COALESCE(?, description), price=COALESCE(?, price), " +
            "duration=COALESCE(?, duration), last_update_date=? WHERE id=?";

    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";

    private static final String READ_CERTIFICATE_BY_TAG = "SELECT cert.name AS cert_name, " +
            "cert.description AS description, cert.id AS id, " +
            "cert.duration AS duration , cert.price AS price , cert.id AS cert_id, " +
            "cert.create_date AS create_date, cert.last_update_date AS last_update_date, " +
            "tag.name AS tag_name, tag.id  AS tag_id FROM gift_certificate AS cert " +
            "LEFT OUTER JOIN gift_certificate_tag AS cert_tag ON cert.id = cert_tag.certificate_id " +
            "LEFT OUTER JOIN tag ON tag.name = COALESCE(?, tag.name)";

    private static final String READ_CERTIFICATE_BY_PARAMS = "SELECT description , duration ,  price , id , " +
            "name, create_date, last_update_date " +
            "FROM gift_certificate WHERE name LIKE CONCAT('%', COALESCE(?, name), '%') " +
            "AND description=COALESCE(?, description)";

    private JdbcTemplate jdbcTemplate;

    private GiftCertificateDtoConverter converter;

    private GiftCertificateMapper certificateMapper;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setConverter(GiftCertificateDtoConverter converter) {

        this.converter = converter;
    }

    @Autowired
    public void setCertificateMapper(GiftCertificateMapper certificateMapper) {

        this.certificateMapper = certificateMapper;
    }

    @Override
    public Long insert(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = converter.convertToEntity(certificateDto);
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
        //can't be null, because id is always auto-generated or inserted manually
        return keyHolder.getKey().longValue();
    }


    @Override
    public void update(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = converter.convertToEntity(certificateDto);
        jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(),
                certificate.getLastUpdateDate(), certificate.getId());
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public Optional<GiftCertificate> read(long id) {

        List<GiftCertificate> certificates = jdbcTemplate.query(READ_CERTIFICATE_BY_ID, new Object[]{id},
                new int[]{Types.INTEGER}, certificateMapper);
        return Optional.ofNullable(certificates.get(0));
    }

    @Override
    public Optional<GiftCertificate> readCertificateByName(String certificateName) {

        List<GiftCertificate> certificates = jdbcTemplate.query(READ_CERTIFICATE_BY_NAME, new Object[]{certificateName},
                new int[]{Types.VARCHAR}, certificateMapper);
        return Optional.ofNullable(certificates.get(0));
    }

    @Override
    public List<GiftCertificate> findCertificateByParams(GiftCertificateDto certificateDto) {

        GiftCertificate certificate = converter.convertToEntity(certificateDto);
        return jdbcTemplate.query(READ_CERTIFICATE_BY_PARAMS, ps -> {
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
        }, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> findCertificateByTagName(String tagName) {

        return jdbcTemplate.query(READ_CERTIFICATE_BY_TAG, ps -> ps.setString(1, tagName),
                new GiftCertificateMapper());
    }


}
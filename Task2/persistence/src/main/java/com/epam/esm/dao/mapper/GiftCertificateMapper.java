package com.epam.esm.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.entity.GiftCertificate;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong("id"));
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        certificate.setDescription(rs.getString("description"));
        certificate.setName(rs.getString("name"));
        certificate.setPrice(rs.getBigDecimal("price"));
        certificate.setDuration(rs.getInt("duration"));
        return certificate;
    }
}

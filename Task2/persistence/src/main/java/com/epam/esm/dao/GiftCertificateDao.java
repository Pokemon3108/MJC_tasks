package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateDao implements Dao<GiftCertificate> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_CERTIFICATE="INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public long create(GiftCertificate obj) {
        return jdbcTemplate.update(CREATE_CERTIFICATE, obj.getName(), obj.getDescription(), obj.getPrice(),
                obj.getDuration(), obj.getCreateDate(), obj.getLastUpdateDate());
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
}

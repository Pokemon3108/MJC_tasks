package com.epam.esm.repos;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

@org.springframework.stereotype.Repository
public class GiftCertificateRepository implements Repository<GiftCertificate> {

    @Override
    public GiftCertificate read(long id) {
        return null;
    }

    @Override
    public void add(GiftCertificate obj) {

    }

    @Override
    public void update(GiftCertificate obj) {

    }

    @Override
    public void remove(GiftCertificate obj) {

    }

    @Override
    public List<GiftCertificate> findAll() {
        return null;
    }
}

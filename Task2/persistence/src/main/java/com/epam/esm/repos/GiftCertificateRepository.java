package com.epam.esm.repos;

import com.epam.esm.dao.Dao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository
public class GiftCertificateRepository implements Repository<GiftCertificate> {

    @Autowired
    private Dao<GiftCertificate> certificateDao;

    @Autowired
    private Dao<Tag> tagDao;

    @Override
    public GiftCertificate read(long id) {
        return null;
    }

    @Override
    public long add(GiftCertificate obj) {
        long certificateId =certificateDao.create(obj);
        obj.getTags().stream().filter(tag-> tagDao.read(tag.getId())==null)
                .forEach(tag->tagDao.create(tag));
        return certificateId;
    }

    @Override
    public void update(long id) {

    }

    @Override
    public void remove(GiftCertificate obj) {

    }

    @Override
    public List<GiftCertificate> findAll() {
        return null;
    }
}

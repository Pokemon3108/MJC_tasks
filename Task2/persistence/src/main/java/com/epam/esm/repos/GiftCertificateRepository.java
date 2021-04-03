package com.epam.esm.repos;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository
public class GiftCertificateRepository implements Repository<GiftCertificate> {

    @Autowired
    private GiftCertificateDao certificateDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public GiftCertificate read(long id) {
        return null;
    }

    @Override
    public long add(GiftCertificate obj) {
        long certificateId = certificateDao.insert(obj);
        obj.getTags().stream().filter(tag -> tagDao.read(tag.getId()) == null)
                .forEach(tag -> tagDao.insert(tag));
        certificateDao.insertCertificateTag(obj);
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

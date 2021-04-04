package com.epam.esm;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagDao tagDao;

    public Tag findTagByName(Tag tag) {
        return tagDao.findTagByName(tag.getName());
    }

    public Long insert(Tag tag) {
        return tagDao.insert(tag);
    }
}

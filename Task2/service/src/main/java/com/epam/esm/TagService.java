package com.epam.esm;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
    Tag findTagByName(Tag tag);

    Long insert(Tag tag);
}

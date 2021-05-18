package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.AdminService;
import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;

/**
 * Implementation of admin service
 */
@Service
public class AdminServiceImpl implements AdminService {

    private TagService tagService;

    @Autowired
    public AdminServiceImpl(TagService tagService) {

        this.tagService = tagService;
    }

    @Override
    public TagDto getMostPopularTagOfRichestUser() {

        return tagService.readMostPopularTag();
    }
}

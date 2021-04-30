package com.epam.esm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.AdminService;
import com.epam.esm.TagService;
import com.epam.esm.UserService;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

@Service
public class AdminServiceImpl implements AdminService {

    private UserService userService;

    private TagService tagService;

    @Autowired
    public AdminServiceImpl(UserService userService, TagService tagService) {

        this.userService = userService;
        this.tagService = tagService;
    }

    @Override
    public Tag getMostPopularTagOfRichestUser() {

        User user = userService.readRichest();
        return tagService.readMostPopularTag(user);
    }
}

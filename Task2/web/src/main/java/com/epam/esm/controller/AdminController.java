package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.AdminService;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.assembler.TagModelAssembler;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    private TagModelAssembler tagModelAssembler;

    @Autowired
    public AdminController(AdminService adminService, TagModelAssembler tagModelAssembler) {

        this.adminService = adminService;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping("/tags/mostPopular")
    public TagModel getMostPopularTag() {

        return tagModelAssembler.toModel(adminService.getMostPopularTagOfRichestUser());
    }
}

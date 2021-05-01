package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.AdminService;
import com.epam.esm.dto.TagDto;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @GetMapping("/tags/mostPopular")
    public TagDto getMostPopularTag() {

        return adminService.getMostPopularTagOfRichestUser();
    }
}

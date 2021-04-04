package com.epam.esm.controller;

import com.epam.esm.impl.GiftCertificateServiceImpl;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/certificate")
public class CertificateController {

    @Autowired
    private GiftCertificateServiceImpl service;

    @PostMapping
    public Long create(@RequestBody GiftCertificate certificate) {
        return service.add(certificate);
    }

    @GetMapping("/{id}")
    public GiftCertificate read(@PathVariable long id) {
        return null;
    }
}

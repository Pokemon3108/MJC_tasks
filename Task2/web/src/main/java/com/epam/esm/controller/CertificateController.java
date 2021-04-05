package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("certificate")
public class CertificateController {

    @Autowired
    private GiftCertificateService service;

    @PostMapping
    public Long create(@RequestBody GiftCertificate certificate) {
        return service.add(certificate);
    }

    @GetMapping("/{id}")
    public GiftCertificate read(@PathVariable long id) {
        return service.read(id);
    }

}

package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/certificate")
public class CertificateController {

    @Autowired
    private GiftCertificateService service;

    @PostMapping("/")
    public Long create(@RequestBody GiftCertificate certificate) {
        return service.add(certificate);
    }
}

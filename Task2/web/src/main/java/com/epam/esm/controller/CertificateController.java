package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("certificate")
public class CertificateController {

    @Autowired
    private GiftCertificateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody GiftCertificate certificate) {
        return service.add(certificate);
    }

    @GetMapping("/{id}")
    public GiftCertificate read(@PathVariable long id) {
        return service.read(id);
    }

    @PutMapping
    public GiftCertificate update(@RequestBody GiftCertificate certificate) {
        service.update(certificate);
        return service.read(certificate.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}

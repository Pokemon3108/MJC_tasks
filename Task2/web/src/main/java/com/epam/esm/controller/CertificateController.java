package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.NotFullCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("certificate")
public class CertificateController {

    @Autowired
    private GiftCertificateService service;

    @Autowired
    @Qualifier("certificateValidator")
    private Validator certificateValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(certificateValidator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody GiftCertificate certificate, BindingResult bindingResult) {
        certificateValidator.validate(certificate, bindingResult);
        if (bindingResult.hasErrors()){
            ObjectError error=bindingResult.getAllErrors().get(0);
            throw new NotFullCertificateException(error.getCode());
        }
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

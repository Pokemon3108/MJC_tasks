package com.epam.esm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotFullCertificateException;

@RestController
@RequestMapping("certificate")
public class CertificateController {

    private GiftCertificateService certificateService;

    private Validator certificateValidator;

    @Autowired
    public void setCertificateService(GiftCertificateService certificateService) {

        this.certificateService = certificateService;
    }

    @Autowired
    @Qualifier("certificateValidator")
    public void setCertificateValidator(Validator certificateValidator) {

        this.certificateValidator = certificateValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(certificateValidator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody GiftCertificateDto certificate, BindingResult bindingResult) {

        certificateValidator.validate(certificate, bindingResult);
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            throw new NotFullCertificateException(error.getCode());
        }
        return certificateService.add(certificate);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto read(@PathVariable long id) {

        return certificateService.read(id);
    }

    @PatchMapping
    public GiftCertificateDto update(@RequestBody GiftCertificateDto certificate) {

        certificateService.update(certificate);
        return certificateService.read(certificate.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        certificateService.delete(id);
    }

    @GetMapping
    public List<GiftCertificateDto> getCertificates(@RequestParam(required = false) String name,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "") String sortParams,
            @RequestParam(defaultValue = "asc") String direction) {

        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.addTag(new Tag(tag));
        List<GiftCertificateDto> certificates = certificateService.findByParams(certificate);
        List<String> splitParams = Arrays.asList(sortParams.split(","));
        return certificateService.sortByParams(certificates, splitParams, direction);
    }
}

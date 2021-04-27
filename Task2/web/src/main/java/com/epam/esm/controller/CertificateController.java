package com.epam.esm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.comparator.Direction;
import com.epam.esm.comparator.GiftCertificateSortService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.IdDto;
import com.epam.esm.exception.NotFullCertificateException;
import com.epam.esm.impl.SearchParamsService;

@RestController
@RequestMapping("certificate")
public class CertificateController {

    private GiftCertificateService certificateService;

    private Validator certificateValidator;

    private GiftCertificateSortService sortService;

    private SearchParamsService searchParamsService;

    @Autowired
    public CertificateController(GiftCertificateService certificateService,
            Validator certificateValidator, GiftCertificateSortService sortService,
            SearchParamsService searchParamsService) {

        this.certificateService = certificateService;
        this.certificateValidator = certificateValidator;
        this.sortService = sortService;
        this.searchParamsService = searchParamsService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(certificateValidator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@RequestBody GiftCertificateDto certificate, BindingResult bindingResult) {

        certificateValidator.validate(certificate, bindingResult);
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            throw new NotFullCertificateException(error.getCode());
        }
        return new IdDto(certificateService.add(certificate));
    }

    @GetMapping("/{id}")
    public GiftCertificateDto read(@PathVariable Long id) {

        return certificateService.read(id);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto update(@PathVariable long id, @RequestBody GiftCertificateDto certificate) {

        certificate.setId(id);
        certificateService.update(certificate);
        return certificateService.read(certificate.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        certificateService.delete(id);
    }

    @GetMapping
    public List<GiftCertificateDto> getCertificates(@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "") String sortParams,
            @RequestParam(defaultValue = "asc") String direction) {

        GiftCertificateDto certificate = searchParamsService.buildDto(name, description, tags);

        List<GiftCertificateDto> certificates = certificateService.findByParams(page, size, certificate);
        List<String> splitParams = Arrays.asList(sortParams.split(","));
        return sortService.sort(certificates, splitParams, Direction.valueOf(direction.toUpperCase()));
    }
}

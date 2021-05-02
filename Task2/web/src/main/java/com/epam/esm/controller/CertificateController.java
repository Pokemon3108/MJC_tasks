package com.epam.esm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import com.epam.esm.SearchParamsService;
import com.epam.esm.comparator.Direction;
import com.epam.esm.comparator.GiftCertificateSortService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.IdDto;
import com.epam.esm.exception.certificate.NotFullCertificateException;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.assembler.GiftCertificateModelAssembler;


/**
 * CertificateController - REST controller for operations with certificates
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private GiftCertificateService certificateService;

    private Validator certificateValidator;

    private GiftCertificateSortService sortService;

    private SearchParamsService searchParamsService;

    private GiftCertificateModelAssembler certificateModelAssembler;

    @Autowired
    public CertificateController(GiftCertificateService certificateService,
            Validator certificateValidator, GiftCertificateSortService sortService,
            SearchParamsService searchParamsService, GiftCertificateModelAssembler certificateModelAssembler) {

        this.certificateService = certificateService;
        this.certificateValidator = certificateValidator;
        this.sortService = sortService;
        this.searchParamsService = searchParamsService;
        this.certificateModelAssembler = certificateModelAssembler;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(certificateValidator);
    }

    /**
     * Create certificate
     *
     * @param certificate   dto object with certificate properties
     * @param bindingResult uses for validation
     * @return dto object with generated id
     */
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

    /**
     * Read certificate
     *
     * @param id the certificate id
     * @return filled certificate
     */
    @GetMapping("/{id}")
    public GiftCertificateModel read(@PathVariable Long id) {

        return certificateModelAssembler.toModel(certificateService.read(id));
    }

    /**
     * Update certificate
     *
     * @param id          of updated certificate
     * @param certificate with properties for update
     * @return updated certificate
     */
    @PatchMapping("/{id}")
    public GiftCertificateModel update(@PathVariable Long id, @RequestBody GiftCertificateDto certificate) {

        certificate.setId(id);
        certificateService.update(certificate);
        return certificateModelAssembler.toModel(certificateService.read(certificate.getId()));
    }

    /**
     * Delete certificate
     *
     * @param id of deleted certificate
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        certificateService.delete(id);
    }

    /**
     * Search and sort certificate
     *
     * @param page        number of certificate group
     * @param size        maximum amount of certificates in response
     * @param name        certificate name
     * @param tags        names of certificate tags, separated by commas
     * @param description certificate description
     * @param sortParams  parameters of sorting, separated by commas
     * @param direction   of sorting
     * @return list of searchable certificates with hateoas links
     */
    @GetMapping
    public CollectionModel<GiftCertificateModel> getCertificates(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "") String sortParams,
            @RequestParam(defaultValue = "asc") String direction) {

        GiftCertificateDto certificate = searchParamsService.buildDto(name, description, tags);

        List<GiftCertificateDto> certificates = certificateService.findByParams(page, size, certificate);
        List<String> splitParams = Arrays.asList(sortParams.split(","));
        return certificateModelAssembler.toCollectionModel(
                sortService.sort(certificates, splitParams, Direction.valueOf(direction.toUpperCase())));
    }
}

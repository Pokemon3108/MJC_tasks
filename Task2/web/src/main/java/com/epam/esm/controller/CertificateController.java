package com.epam.esm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.DtoBuilderService;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.PageService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SortParamsDto;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.PageCertificateModel;
import com.epam.esm.model.assembler.GiftCertificateModelAssembler;
import com.epam.esm.validator.GiftCertificateValidator;


/**
 * CertificateController - REST controller for operations with certificates
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private GiftCertificateService certificateService;

    private GiftCertificateValidator certificateValidator;

    private DtoBuilderService dtoBuilderService;

    private GiftCertificateModelAssembler certificateModelAssembler;

    private PageService pageService;

    @Autowired
    public CertificateController(GiftCertificateService certificateService,
            GiftCertificateValidator certificateValidator,
            DtoBuilderService dtoBuilderService, GiftCertificateModelAssembler certificateModelAssembler,
            PageService pageService) {

        this.certificateService = certificateService;
        this.certificateValidator = certificateValidator;
        this.dtoBuilderService = dtoBuilderService;
        this.certificateModelAssembler = certificateModelAssembler;
        this.pageService = pageService;
    }

    /**
     * Create certificate
     *
     * @param certificate dto object with certificate properties
     * @return dto object with generated id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateModel create(@RequestBody GiftCertificateDto certificate) {

        certificateValidator.validateCreation(certificate);
        return certificateModelAssembler.toModel(certificateService.add(certificate));
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
        certificateValidator.validateUpdate(certificate);
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
    public ResponseEntity<?> delete(@PathVariable long id) {

        certificateService.delete(id);
        return ResponseEntity.noContent().build();
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
    public PageCertificateModel getCertificates(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "") String sortParams,
            @RequestParam(defaultValue = "asc") String direction) {

        GiftCertificateDto certificate = dtoBuilderService.buildCertificateDto(name, description, tags);
        SortParamsDto sortParamsDto = dtoBuilderService.buildSortParams(sortParams, direction);

        List<GiftCertificateDto> certificates = certificateService.findByParams(page, size, certificate, sortParamsDto);

        return new PageCertificateModel(certificateModelAssembler.toCollectionModel(certificates),
                pageService.buildPageForCertificateSearch(page, size, certificate, certificates));
    }
}

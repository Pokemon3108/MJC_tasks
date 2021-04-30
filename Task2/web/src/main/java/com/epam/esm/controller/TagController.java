package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.TagService;
import com.epam.esm.dto.IdDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.certificate.NotFullCertificateException;

/**
 * TagController - REST controller for operations with tags
 */
@RestController
@RequestMapping("tags")
public class TagController {

    private TagService tagService;

    private Validator tagValidator;

    /**
     * Sets tag service.
     *
     * @param tagService
     */
    @Autowired
    public void setTagService(TagService tagService) {

        this.tagService = tagService;
    }

    /**
     * Sets tag validator.
     *
     * @param tagValidator
     */
    @Autowired
    @Qualifier("tagValidator")
    public void setTagValidator(Validator tagValidator) {

        this.tagValidator = tagValidator;
    }

    /**
     * Create tag
     *
     * @param tag           that will be created
     * @param bindingResult registers error
     * @return the id of tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@RequestBody Tag tag, BindingResult bindingResult) {

        tagValidator.validate(tag, bindingResult);
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            throw new NotFullCertificateException(error.getCode());
        }
        return new IdDto(tagService.create(tag));
    }

    /**
     * Read tag
     *
     * @param id of tag
     * @return the filled tag
     */
    @GetMapping("/{id}")
    public Tag read(@PathVariable long id) {

        return tagService.readTagById(id);
    }

    /**
     * Delete tag
     *
     * @param id of tag
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        tagService.delete(id);
    }
}

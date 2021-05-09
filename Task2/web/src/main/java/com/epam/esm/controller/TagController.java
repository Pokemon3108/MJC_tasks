package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.certificate.NotFullCertificateException;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.assembler.TagModelAssembler;

/**
 * TagController - REST controller for operations with tags
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    private Validator tagValidator;

    private TagModelAssembler tagModelAssembler;

    @Autowired
    public TagController(TagService tagService, Validator tagValidator,
            TagModelAssembler tagModelAssembler) {

        this.tagService = tagService;
        this.tagValidator = tagValidator;
        this.tagModelAssembler = tagModelAssembler;
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
    public TagModel create(@RequestBody TagDto tag, BindingResult bindingResult) {

        tagValidator.validate(tag, bindingResult);
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            throw new NotFullCertificateException(error.getCode());
        }
        return tagModelAssembler.toModel(tagService.create(tag));
    }

    /**
     * Read tag
     *
     * @param id of tag
     * @return the filled tag
     */
    @GetMapping("/{id}")
    public TagModel read(@PathVariable long id) {

        return tagModelAssembler.toModel(tagService.readTagById(id));
    }

    /**
     * Delete tag
     *
     * @param id of tag
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable long id) {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

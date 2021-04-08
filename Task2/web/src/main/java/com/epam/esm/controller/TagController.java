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
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotFullCertificateException;

@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    @Qualifier("tagValidator")
    private Validator tagValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Tag tag, BindingResult bindingResult) {

        tagValidator.validate(tag, bindingResult);
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            throw new NotFullCertificateException(error.getCode());
        }
        return tagService.create(tag);
    }

    @GetMapping("/{id}")
    public Tag read(@PathVariable long id) {

        return tagService.readTagById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        tagService.delete(id);
    }
}

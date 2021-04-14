package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.esm.LocaleService;
import com.epam.esm.error.Error;
import com.epam.esm.error.ErrorCode;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.exception.NotFullCertificateException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private LocaleService localeService;

    @Autowired
    public void setLocaleService(LocaleService localeService) {

        this.localeService = localeService;
    }

    @ExceptionHandler(NoCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleCertificateNotFound(NoCertificateException ex) {

        return new Error(ErrorCode.NO_CERTIFICATE.getCode(),
                localeService.getLocaleMessage("no_certificate", ex.getCertificateId()));
    }

    @ExceptionHandler(NoIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleIdNotFound(NoIdException ex) {

        return new Error(ErrorCode.NO_ID.getCode(),
                localeService.getLocaleMessage("no_id"));
    }

    @ExceptionHandler(NotFullCertificateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleNotFullCertificate(NotFullCertificateException ex) {

        return new Error(ErrorCode.NOT_FULL_CERTIFICATE.getCode(),
                localeService.getLocaleMessage(ex.getMessage()));
    }

    @ExceptionHandler(NoTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagNotFound(NoTagException ex) {

        return new Error(ErrorCode.NO_TAG.getCode(), localeService.getLocaleMessage("no_certificate", ex.getTagId()));
    }

    @ExceptionHandler(DuplicateCertificateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleDuplicateCertificate(DuplicateCertificateException ex) {

        return new Error(ErrorCode.DUPLICATE_CERTIFICATE_NAME.getCode(),
                localeService.getLocaleMessage("duplicate_certificate", ex.getName()));
    }

    @ExceptionHandler(DuplicateTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleDuplicateCertificate(DuplicateTagException ex) {

        return new Error(ErrorCode.DUPLICATE_TAG_NAME.getCode(),
                localeService.getLocaleMessage("duplicate_tag", ex.getName()));
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Error handleDuplicateCertificate(Exception ex) {
//
//        return new Error(ErrorCode.BASE_ERROR.getCode(),
//                localeService.getLocaleMessage("base_error"));
//    }
}

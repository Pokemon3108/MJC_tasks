package com.epam.esm.controller;

import com.epam.esm.LocaleService;
import com.epam.esm.error.Error;
import com.epam.esm.error.ErrorCode;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LocaleService localeService;

    @ExceptionHandler(NoCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleCertificateNotFound(NoCertificateException ex) {
        return new Error(ErrorCode.NO_CERTIFICATE.getCode(),
                localeService.getLocaleMessage("no_certificate") + ex.getCertificateId());
    }

    @ExceptionHandler(NoIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleIdNotFound(NoIdException ex) {
        return new Error(ErrorCode.NO_ID.getCode(),
                localeService.getLocaleMessage("no_id"));
    }
}

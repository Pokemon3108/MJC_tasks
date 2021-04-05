package com.epam.esm.controller;

import com.epam.esm.error.Error;
import com.epam.esm.error.ErrorCode;
import com.epam.esm.exception.NoCertificateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleCertificateNotFound(NoCertificateException ex) {
        return new Error(ErrorCode.NO_CERTIFICATE.getCode(), "No certificate found");
    }
}

package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.epam.esm.LocaleService;
import com.epam.esm.error.Error;
import com.epam.esm.error.ErrorCode;
import com.epam.esm.exception.NoIdException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.exception.SizeLimitException;
import com.epam.esm.exception.certificate.CertificateIsOrderedException;
import com.epam.esm.exception.certificate.DuplicateCertificateException;
import com.epam.esm.exception.certificate.IllegalCertificateProperties;
import com.epam.esm.exception.certificate.NoCertificateException;
import com.epam.esm.exception.order.CreationOrderException;
import com.epam.esm.exception.order.NoOrderException;
import com.epam.esm.exception.tag.DuplicateTagException;
import com.epam.esm.exception.tag.NoTagException;
import com.epam.esm.exception.user.NoUserWithIdException;
import com.epam.esm.exception.user.NoUsersException;
import com.epam.esm.exception.user.UsersOrderHasNoTags;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(IllegalCertificateProperties.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleNotFullCertificate(IllegalCertificateProperties ex) {

        return new Error(ErrorCode.NOT_FULL_CERTIFICATE.getCode(),
                localeService.getLocaleMessage(ex.getMessage()));
    }

    @ExceptionHandler(NoTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagNotFound(NoTagException ex) {

        return new Error(ErrorCode.NO_TAG.getCode(), localeService.getLocaleMessage("no_tag", ex.getTagId()));
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

    @ExceptionHandler(NoPageException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNoPage(NoPageException ex) {

        return new Error(ErrorCode.NO_PAGE.getCode(),
                localeService.getLocaleMessage("no_page", ex.getPage(), ex.getSize()));
    }

    @ExceptionHandler(CreationOrderException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleCreationOrder(CreationOrderException ex) {

        return new Error(ErrorCode.CREATION_ORDER.getCode(),
                localeService.getLocaleMessage("creation_order_error"));
    }

    @ExceptionHandler(NoUserWithIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNoUser(NoUserWithIdException ex) {

        return new Error(ErrorCode.NO_USER_WITH_ID.getCode(),
                localeService.getLocaleMessage("no_user", ex.getId()));
    }

    @ExceptionHandler(NoUsersException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNoUser(NoUsersException ex) {

        return new Error(ErrorCode.NO_USERS_IN_STORAGE.getCode(),
                localeService.getLocaleMessage("no_users_in_storage"));
    }

    @ExceptionHandler(UsersOrderHasNoTags.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNoUserOrderTags(UsersOrderHasNoTags ex) {

        return new Error(ErrorCode.NO_TAGS_IN_USER_ORDER.getCode(),
                localeService.getLocaleMessage("user_order_has_no_tags", ex.getId()));
    }

    @ExceptionHandler(NoOrderException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNoOrder(NoOrderException ex) {

        return new Error(ErrorCode.NO_ORDER.getCode(),
                localeService.getLocaleMessage("no_order", ex.getOrderId()));
    }

    @ExceptionHandler(CertificateIsOrderedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleDeleteCertificate(CertificateIsOrderedException ex) {

        return new Error(ErrorCode.CERTIFICATE_IS_ORDERED.getCode(),
                localeService.getLocaleMessage("is_ordered_certificate", ex.getId()));
    }

    @ExceptionHandler(SizeLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleMaxSizeLimited(SizeLimitException ex) {

        return new Error(ErrorCode.MAX_SIZE_LIMIT.getCode(),
                localeService.getLocaleMessage("max_size_limit", ex.getMaxSize()));
    }

    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class,
            SessionAuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error handleAuthenticationException(RuntimeException ex) {

        return new Error(ErrorCode.NO_AUTHORIZED.getCode(), "error.authorization");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleUsernameNotFoundException(UsernameNotFoundException ex) {

        return new Error(ErrorCode.NO_USERNAME.getCode(),
                localeService.getLocaleMessage("no_username", ex.getMessage()));
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Error error = new Error(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode(),
                localeService.getLocaleMessage("media_type", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Error error = new Error(ErrorCode.METHOD_NOT_ALLOWED.getCode(),
                localeService.getLocaleMessage("method_not_allowed", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Error error = new Error(ErrorCode.BASE_ERROR.getCode(),
                localeService.getLocaleMessage("base_error"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

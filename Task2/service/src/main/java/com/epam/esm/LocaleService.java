package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleService {

    @Autowired
    private MessageSource messageSource;

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public String getLocaleMessage(String messageName) {
        return messageSource.getMessage(messageName, null, getLocale());
    }
}

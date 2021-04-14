package com.epam.esm;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LocaleService {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {

        this.messageSource = messageSource;
    }

    public Locale getLocale() {

        return LocaleContextHolder.getLocale();
    }

    public String getLocaleMessage(String messageName, Object... args) {

        return messageSource.getMessage(messageName, args, getLocale());
    }
}

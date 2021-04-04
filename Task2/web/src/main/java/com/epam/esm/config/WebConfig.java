package com.epam.esm.config;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.impl.GiftCertificateServiceImpl;
import com.epam.esm.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {
    
    @Bean
    public TagService tagService() {
        return new TagServiceImpl();
    }

    @Bean
    public GiftCertificateService giftCertificateService() {
        return new GiftCertificateServiceImpl();
    }

}

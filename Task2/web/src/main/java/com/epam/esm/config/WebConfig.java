package com.epam.esm.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.comparator.ComparatorService;
import com.epam.esm.comparator.ComparatorServiceImpl;
import com.epam.esm.impl.GiftCertificateServiceImpl;
import com.epam.esm.impl.TagServiceImpl;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder().build()));
    }

    @Bean
    public TagService tagService() {

        return new TagServiceImpl();
    }

    @Bean
    public GiftCertificateService giftCertificateService() {

        return new GiftCertificateServiceImpl();
    }

    @Bean
    public ComparatorService comparatorService() {

        return new ComparatorServiceImpl();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return builder;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {

        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:error_messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

}

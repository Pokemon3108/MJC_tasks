package com.epam.esm.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext webApplicationContext
                = new AnnotationConfigWebApplicationContext();
        webApplicationContext.scan("com.epam.esm");
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("mvc", new DispatcherServlet(webApplicationContext));
        servlet.addMapping("/");
        //TODO: It can be reasonable to have this key-value pair in properties configuration.
        servletContext.setInitParameter(
                "spring.profiles.active", "prod");
    }
}


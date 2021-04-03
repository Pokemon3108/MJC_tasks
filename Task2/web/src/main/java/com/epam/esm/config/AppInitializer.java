package com.epam.esm.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext
                = new AnnotationConfigWebApplicationContext();
        webApplicationContext.scan("com.epam.esm");
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("mvc", new DispatcherServlet(webApplicationContext));
        servlet.addMapping("/");
        servletContext.setInitParameter(
                "spring.profiles.active", "prod");
    }
}


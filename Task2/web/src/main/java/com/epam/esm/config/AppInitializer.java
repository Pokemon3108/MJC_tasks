package com.epam.esm.config;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

    private static final String ACTIVE_PROFILE = "spring.profiles.active";

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext webApplicationContext
                = new AnnotationConfigWebApplicationContext();
        webApplicationContext.scan("com.epam.esm");
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("mvc", new DispatcherServlet(webApplicationContext));
        servlet.addMapping("/");
        ResourceBundle resource = ResourceBundle.getBundle("application");
        servletContext.setInitParameter(ACTIVE_PROFILE, resource.getString(ACTIVE_PROFILE));
    }
}


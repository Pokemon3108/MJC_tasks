package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.epam.esm.controller.handler.ExceptionHandlerFilter;
import com.epam.esm.security.JwtConfigurer;
import com.epam.esm.security.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenFilter tokenFilter;

    private JwtConfigurer jwtConfigurer;

    private UserDetailsService userService;

    private ExceptionHandlerFilter handlerFilter;

    @Autowired
    public SecurityConfig(JwtTokenFilter tokenFilter, JwtConfigurer jwtConfigurer, ExceptionHandlerFilter handlerFilter,
            @Qualifier("userServiceImpl") UserDetailsService userService) {

        this.tokenFilter = tokenFilter;
        this.jwtConfigurer = jwtConfigurer;
        this.userService = userService;
        this.handlerFilter = handlerFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .addFilterBefore(handlerFilter, LogoutFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/auth/login").permitAll()
                .antMatchers("/users/**").authenticated();
//                .and()
//                .apply(jwtConfigurer);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }
}
package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.epam.esm.controller.handler.ExceptionHandlerFilter;
import com.epam.esm.security.JwtConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtConfigurer jwtConfigurer;

    private UserDetailsService userService;

    private ExceptionHandlerFilter handlerFilter;

    private AuthenticationEntryPoint authPoint;

    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(JwtConfigurer jwtConfigurer, ExceptionHandlerFilter handlerFilter,
            @Qualifier("userServiceImpl") UserDetailsService userService, AuthenticationEntryPoint authPoint,
            AccessDeniedHandler accessDeniedHandler) {

        this.jwtConfigurer = jwtConfigurer;
        this.userService = userService;
        this.handlerFilter = handlerFilter;
        this.authPoint = authPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .addFilterBefore(handlerFilter, LogoutFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login", "/users")
                    .hasAnyRole("ANONYMOUS", "ADMIN")
                .antMatchers(HttpMethod.GET, "/certificates/**").permitAll()
                .antMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/orders", "/auth/refreshToken").hasAnyRole("USER", "ADMIN")
                .anyRequest().hasRole("ADMIN")

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authPoint)
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                .apply(jwtConfigurer);
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

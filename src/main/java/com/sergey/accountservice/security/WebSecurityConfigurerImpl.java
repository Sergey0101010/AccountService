package com.sergey.accountservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getEncoder());
    }

    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // manage access
                .antMatchers(HttpMethod.POST, "/api/signup").permitAll()
                .mvcMatchers("api/auth/changepass").authenticated()
                .mvcMatchers("api/empl/payment").hasAnyRole("USER", "ACCOUNTANT")
                .mvcMatchers("api/acct/payments").hasRole("ACCOUNTANT")
                .mvcMatchers("api/admin/**").hasRole("ADMINISTRATOR")
                .and()
                .csrf().disable().headers().frameOptions().disable()// for Postman
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }



    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean //custom 403 message
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");
    }

}

package com.example.pacomerselo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;


@Configuration
@Order(1)
public class RESTSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    RepositoryUserDetailsService repositoryUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(repositoryUserDetailsService).passwordEncoder(new BCryptPasswordEncoder(10,new SecureRandom()));
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception{
        http.antMatcher("/api/**");

        http
                .authorizeRequests()
                        .antMatchers(HttpMethod.GET,"/user").hasAnyRole("ROLE_USER")
                        .antMatchers(HttpMethod.GET,"/cart").hasAnyRole("ROLE_USER")
                        .antMatchers(HttpMethod.PUT,"/changeprofile").hasAnyRole("ROLE_USER")
                        .antMatchers(HttpMethod.GET,"/orders").hasAnyRole("ROLE_USER","ROLE_ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/deleteuser/{username}").hasAnyRole("ROLE_ADMIN")
                        .antMatchers(HttpMethod.GET,"/adminPage").hasAnyRole("ROLE_ADMIN");

        http.csrf().disable();
        http.httpBasic();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}

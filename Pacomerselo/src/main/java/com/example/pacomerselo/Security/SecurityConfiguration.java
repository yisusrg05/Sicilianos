package com.example.pacomerselo.Security;


import com.example.pacomerselo.Controllers.UserAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
    @Autowired
    private RepositoryUserDetailsService userDetailsService;
    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/","/index","/restaurant/{name}","/restaurant","/restaurant/search","/restaurant/{nameRest}/search","/restaurant/{name}/filter","/nosotros","/accessDenied","/reviews","/updateSuccesful","/register","/alreadyExistingUser","/incorrectEmailOrPassword","/registerRestaurant","/faq","/login").permitAll();
        http.authorizeRequests().antMatchers("/oauth2/**").permitAll();

        //private
        http.authorizeRequests().antMatchers("/cart","/addcarrito/{id1}","/deletecart/{id}","/deleteCart","/payment","/profile","/orderPlaced").hasAnyAuthority("ROLE_USER");

        http.authorizeRequests().antMatchers("/restaurant/**","/updateRestaurant/{name}","/deleteRestaurant/{name}","/{name}/registerDish","/adminPage").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().antMatchers("/restaurantControl/**","/restaurantControl").hasAnyAuthority("ROLE_RESTAURANT");
        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().successHandler(userAuthenticationSuccessHandler);
        http.formLogin().failureUrl("/incorrectEmailOrPassword");

        http.oauth2Login().loginPage("/login");
        http.oauth2Login().userInfoEndpoint().userService(oAuth2UserService);
        http.oauth2Login().successHandler(oAuth2LoginSuccessHandler);


        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
    }


}

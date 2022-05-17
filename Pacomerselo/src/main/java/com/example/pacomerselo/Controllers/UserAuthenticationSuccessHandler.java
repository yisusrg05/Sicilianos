package com.example.pacomerselo.Controllers;

import java.io.IOException;
import javax.servlet.ServletException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1,
                                        Authentication authentication) throws IOException, ServletException {

        boolean hasUserRole = false;
        boolean hasRestaurantRole = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")||grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasUserRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_RESTAURANT")) {
                hasRestaurantRole = true;
                break;
            }
        }

        if (hasUserRole) {
            redirectStrategy.sendRedirect(arg0, arg1, "/profile");
        } else if (hasRestaurantRole) {
            redirectStrategy.sendRedirect(arg0, arg1, "/restaurantControl");
        } else {
            throw new IllegalStateException();
        }
    }
}

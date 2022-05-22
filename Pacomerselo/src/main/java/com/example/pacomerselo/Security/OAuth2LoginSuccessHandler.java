package com.example.pacomerselo.Security;

import com.example.pacomerselo.Entities.AuthenticationProvider;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserManager userManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        Optional<User> op = userManager.findByUsername(oAuth2User.getName());
        String name = oAuth2User.getName();
        String clientName= oAuth2User.getClientName();
        OAuth2AuthenticationToken oAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
        if (op.isEmpty()){
            if(Objects.equals(clientName, AuthenticationProvider.GOOGLE.name())){
                userManager.addOAuthUser(name,email, AuthenticationProvider.GOOGLE ,oAuth2AuthenticationToken);
            }
            else{
                userManager.addOAuthUser(name,email, AuthenticationProvider.FACEBOOK ,oAuth2AuthenticationToken);
            }

        }else{
            if(Objects.equals(clientName, AuthenticationProvider.GOOGLE.name())){
                userManager.updateOAuthUser(name, email,AuthenticationProvider.GOOGLE);
            }
            else{
                userManager.updateOAuthUser(name, email,AuthenticationProvider.FACEBOOK);

            }
        }
        response.sendRedirect("/profile");

        System.out.println("User´s email: " + email);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}

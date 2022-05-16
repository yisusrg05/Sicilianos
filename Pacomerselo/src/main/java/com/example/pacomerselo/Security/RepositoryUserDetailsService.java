package com.example.pacomerselo.Security;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Repositories.Restaurant.RestaurantRepository;
import com.example.pacomerselo.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElse(null);
        Restaurant restaurant = restaurantRepository.findByName(username).orElse(null);
        List<GrantedAuthority> roles = new ArrayList<>();

        if(user!=null){
            for (String role : user.getRoles()) {
                roles.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        }
        else if(restaurant!=null){
            for (String role : restaurant.getRoles()) {
                roles.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return new org.springframework.security.core.userdetails.User(restaurant.getName(), restaurant.getPassword(), roles);

        }
        else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        }
}
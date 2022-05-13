package com.example.pacomerselo.Managers;

import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseUsersLoader {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    private void initDatabase() {
        userRepository.save(new User("Yisus", "Jesus", "Ramirez", "yisus@urjc.es", passwordEncoder.encode("oleole"), List.of("USER")));
        userRepository.save(new User("Administrador","Paco","Mérselo","admin@pacomerselo.me", passwordEncoder.encode("adminpass"), Arrays.asList("USER", "ADMIN")));
    }
}

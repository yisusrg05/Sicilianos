package com.example.pacomerselo;

import com.example.pacomerselo.Entities.*;
import com.example.pacomerselo.Repositories.Dishes.DishesRepository;
import com.example.pacomerselo.Repositories.Order.OrderRepository;
import com.example.pacomerselo.Repositories.Restaurant.RestaurantRepository;
import com.example.pacomerselo.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PacomerseloApplication {

    public static void main(String[] args) {
            ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(PacomerseloApplication.class, args);
    }
}

package com.example.pacomerselo.Entities;

import com.example.pacomerselo.Entities.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.service.spi.InjectService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    /*The entity user has his own attributes: username (as we want the user get registered with it), name, surname,
    email, password, a Map called orders: it's key is a long, which is an ID to identify de order and the Value
    is an Order. Order is an entity that has a List with the dishes that contains that order. Also, We have a List of
    Dish: it contains the dishes that the user want to order. Also, as we want to know if a user is a consumer or is
    an Admin, that can create Restaurants, we have a boolean to know what category the User is. Also, we have an ID to identify also a user.
     */
    @Id
    private String username;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authProvider;

    //private List<Dishes> cart;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonManagedReference
    @JsonIgnore
    private List<Order> orders;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    //Constructor of user:
    public User(String username,String name, String surname, String email, String password,String... roles){
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.orders=new ArrayList<>();
        this.roles= List.of(roles);
    }

    public User(String username,String name, String surname, String email, String password){
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.orders=new ArrayList<>();
        this.roles=new ArrayList<>();
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

    public AuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }
}

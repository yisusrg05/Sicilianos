package com.example.pacomerselo;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
@Getter
@Setter

public class User {

    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Boolean admin;
    private Map<Long,Order> orders;
    private List<Dishes> cart;
    private long id=-1;


    public User(String username,String name, String surname, String email, String password){
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.admin=false;
        this.orders=new ConcurrentHashMap<>();
        this.cart=new ArrayList<>();
    }

    public void addDish(Dishes dish){
        this.cart.add(dish);
    }

    public Collection<Dishes> allCart(){
        return this.cart;
    }

    public void addOrder(long id, Order order){
        this.orders.put(id,order);
    }
}

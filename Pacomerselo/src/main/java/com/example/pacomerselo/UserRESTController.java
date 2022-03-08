package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserRESTController{

    @Autowired
    UserHolder userHolder;

    @GetMapping("/cart")
    public Collection<Dishes> getDishes(){
        return userHolder.getDishes(1);
    }

    @GetMapping("/orders")
    public Collection<Order> getOrders(){
        return userHolder.getUser(1).getOrders().values();
    }

}
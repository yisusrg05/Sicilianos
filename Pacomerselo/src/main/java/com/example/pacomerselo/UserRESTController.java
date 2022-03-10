package com.example.pacomerselo;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRESTController{

    @Autowired
    UserHolder userHolder;
    RestaurantHolder restaurantHolder;

    //////////////////////CART REST CONTROLLER//////////////////////

    @GetMapping("/cart")
    public Collection<Dishes> getDishes(){
        return userHolder.getDishes(1);
    }

    @PostMapping("/addcart/{id1}/{id2}")
    public ResponseEntity<Dishes> addToCart(@PathVariable long id1, @PathVariable long id2){
        if(restaurantHolder.getDish(id1,id2)!=null){
            userHolder.addDishToCart(1,restaurantHolder.getDish(id1,id2));
            return new ResponseEntity<>(restaurantHolder.getDish(id1,id2), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/deletedish/{id}")
    public ResponseEntity<Dishes> deleteDishFromCart(@PathVariable long id){
        if(userHolder.getDishFromCart(id)!=null){
            userHolder.deleteDishFromCart(id, userHolder.getDishFromCart(id));
            return new ResponseEntity<>(userHolder.getDishFromCart(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletecart")
    public ResponseEntity<List<Dishes>> deleteAllCart(){
        if(userHolder.getDishes(1)!=null){
            userHolder.deleteCart(1);
            return new ResponseEntity<>(userHolder.getDishes(1), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //////////////////////ORDERS REST CONTROLLER//////////////////////

    @GetMapping("/orders")
    public Collection<Order> getOrders(){
        return userHolder.getUser(1).getOrders().values();
    }

    //////////////////////USER REST CONTROLLER//////////////////////

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id){
        return userHolder.getUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        userHolder.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/changeprofile{id}/")
    public ResponseEntity<User> updateProfile(@PathVariable long id,@RequestBody User newUser){
        User oldUser= userHolder.getUser(id);
        if (oldUser!= null) {
            newUser.setId(id);
            userHolder.updateUser(id,newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = userHolder.removeUser(id);
        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
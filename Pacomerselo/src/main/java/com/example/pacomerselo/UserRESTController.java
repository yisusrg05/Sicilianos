package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserRESTController{

    @Autowired
    UserHolder userHolder;

    //////////////////////CART REST CONTROLLER//////////////////////

    @GetMapping("/cart")
    public Collection<Dishes> getDishes(){
        return userHolder.getDishes(1);
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
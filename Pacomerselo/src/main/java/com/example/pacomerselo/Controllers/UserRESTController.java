package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.Order;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Managers.RestaurantManager;
import com.example.pacomerselo.Managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserRESTController{

    @Autowired
    UserManager userManager;
    @Autowired
    RestaurantManager restaurantManager;

    //////////////////////ORDERS REST CONTROLLER//////////////////////

    //Get all the orders of the user
    @GetMapping("/orders/{username}")
    public Collection<Order> getOrders(@PathVariable String username){
        return userManager.findOrdersByUsername(username);
    }

    //////////////////////USER REST CONTROLLER//////////////////////

    //Get the user of the Username given
    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username){
        return userManager.getUser(username);
    }


    //Add a new user to the App
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        userManager.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //Update a user profile given (Username)
    @PutMapping("/changeprofile/{username}")
    public ResponseEntity<User> updateProfile(@PathVariable String username,@RequestBody User newUser){
        int usersUpdated=userManager.updateUser(username,newUser);
        if (usersUpdated==1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the user given (Username)
    @DeleteMapping("/deleteuser/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) {
        User user = userManager.removeUser(username);
        if(user != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
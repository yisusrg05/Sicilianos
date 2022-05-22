package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.Order;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Managers.RestaurantManager;
import com.example.pacomerselo.Managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping("/orders")
    public ResponseEntity<Collection<Order>>getOrders(HttpServletRequest request){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            String username=SecurityContextHolder.getContext().getAuthentication().getName();
            if(request.isUserInRole("ROLE_ADMIN")){
                return new ResponseEntity<>(userManager.getOrders(),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(userManager.findOrdersByUsername(username),HttpStatus.OK);

            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //////////////////////USER REST CONTROLLER//////////////////////

    //Get the user of the Username given
    @GetMapping("/user")
    public ResponseEntity<User> getUser(HttpServletRequest request){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            String username=SecurityContextHolder.getContext().getAuthentication().getName();
            return new ResponseEntity<>(userManager.getUser(username),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    //Add a new user to the App
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        userManager.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //Update a user profile given (Username)
    @PutMapping("/changeprofile")
    public ResponseEntity<User> updateProfile(@RequestBody User newUser){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
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
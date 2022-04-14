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
    UserManager userManager;
    @Autowired
    RestaurantManager restaurantManager;


    //////////////////////CART REST CONTROLLER//////////////////////
    /*
    //Get the cart
    @GetMapping("/cart")
    public Collection<Dishes> getDishes(){
        return userHolder.getDishes(1);
    }

    //Add a new dish (ID2) of a restaurant (ID1) to the cart
    @PostMapping("/addcart/{id1}/{id2}")
    public ResponseEntity<Dishes> addToCart(@PathVariable long id1, @PathVariable long id2){
        if(restaurantHolder.getDish(id1,id2)!=null){
            userHolder.addDishToCart(1,restaurantHolder.getDish(id1,id2));
            return new ResponseEntity<>(restaurantHolder.getDish(id1,id2), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update the cart, deleting a single dish of the cart (ID)
    @PutMapping("/deletedish/{id}")
    public ResponseEntity<Dishes> deleteDishFromCart(@PathVariable long id){
        if(userHolder.getDishFromCart(1,id)!=null){
            userHolder.deleteDishFromCart(1, userHolder.getDishFromCart(1,id));
            return new ResponseEntity<>(userHolder.getDishFromCart(1,id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete all the cart
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
    */
    //////////////////////ORDERS REST CONTROLLER//////////////////////

    //Get all the orders of the user
    @GetMapping("/orders")
    public Collection<Order> getOrders(){
        return userManager.getOrders(1);
    }

    /*
    //Add a new order to the user
    @PostMapping("/proccessOrder")
    public ResponseEntity<Order> proccessOrder(){
        if(userHolder.getUser(1).getCart()!=null){
            long id=userHolder.proccessOrder(1);
            return new ResponseEntity<>(userHolder.getOrders(1).get(id),HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    */

    //////////////////////USER REST CONTROLLER//////////////////////

    //Get the user of the ID given
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id){
        return userManager.getUser(id);
    }

    /*
    //Log in a user
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password){
        if(userHolder.validUser(username,password)){
            return new ResponseEntity<>(userHolder.getUser(username),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */

    //Add a new user to the App
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        userManager.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //Update a user profile given (ID)
    @PutMapping("/changeprofile/{id}/")
    public ResponseEntity<User> updateProfile(@PathVariable long id,@RequestBody User newUser){
        User user= userManager.updateUser(id,newUser);
        if (user!= null) {
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the user given (ID)
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = userManager.removeUser(id);
        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
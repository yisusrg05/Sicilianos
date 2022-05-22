package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Managers.RestaurantManager;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestMapping("/api")
@RestController
public class RestaurantRESTController {

    @Autowired
    RestaurantManager restaurantManager;


    //////////////////////RESTAURANTS REST CONTROLLER//////////////////////

    //Add a new Restaurant to the catalog
    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> newRestaurant(@RequestBody Restaurant restaurant){
        restaurantManager.addRestaurant(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    //Get the list of all the restaurants
    @GetMapping("/restaurant")
    public Collection<Restaurant> getRestaurants(){
        return restaurantManager.getRestaurants();
    }

    //Get the restaurant given (ID)
    @GetMapping("/restaurant/{name}")
    public ResponseEntity<Restaurant> getRestaurants(@PathVariable String name){
        Restaurant restaurant = restaurantManager.getRestaurant(name);
        if(restaurant != null){
            return new ResponseEntity<>(restaurant,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update the restaurant given (ID)
    @PutMapping("/restaurant")
    public ResponseEntity<Restaurant> updateRestaurant(HttpServletRequest request, @RequestBody Restaurant newRestaurant) {
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        if(request.isUserInRole("ROLE_RESTAURANT")){
            restaurantManager.updateRestaurant(name,newRestaurant);
            return new ResponseEntity<>(restaurantManager.getRestaurant(name), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //Delete the restaurant given (ID)
    @DeleteMapping("/restaurant")
    public ResponseEntity<Restaurant> deleteRestaurant(HttpServletRequest request) {
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        if(request.isUserInRole("ROLE_RESTAURANT")){
            restaurantManager.removeRestaurant(name);
            return new ResponseEntity<>(restaurantManager.getRestaurant(name), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/restaurant/{name}")
    public ResponseEntity<Restaurant> deleteRestaurant(HttpServletRequest request, @PathVariable String name) {
        if(request.isUserInRole("ROLE_ADMIN")){
            restaurantManager.removeRestaurant(name);
            return new ResponseEntity<>(restaurantManager.getRestaurant(name), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/restaurant/search/{name}")
    public Collection<Restaurant> restaurantSearch(@PathVariable String name){
        return restaurantManager.findRestaurantByName(name);
    }

    //////////////////////DISHES REST CONTROLLER//////////////////////
    //Add a new dish to the restaurant catalog (given the ID)
    @PostMapping("/addDishes")
    public ResponseEntity<Dishes> newDish(HttpServletRequest request, @RequestBody Dishes dish){
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        if(request.isUserInRole("ROLE_RESTAURANT")){
            restaurantManager.addDish(name,dish);
            return new ResponseEntity<>(dish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //Get all the dishes of the restaurant catalog (given the ID)
    @GetMapping("/restaurant/{name}/dishes")
    public Collection<Dishes> getDishes(@PathVariable String name){
        return restaurantManager.getDishes(name);
    }

    //Get the dish (ID2) of the restaurant catalog (ID1)
    @GetMapping("/restaurant/{name}/dishes/{id2}")
    public ResponseEntity<Dishes> getDish(@PathVariable String name,@PathVariable long id2){
        Dishes dish = restaurantManager.getDish(id2);
        if(dish != null){
            return new ResponseEntity<>(dish,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update the dish (ID2) of the restaurant catalog (ID1)
    @PutMapping("/updateDish/{id2}")
    public ResponseEntity<Dishes> updateDish(HttpServletRequest request, @PathVariable long id2, @RequestBody Dishes newDish){
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        if(request.isUserInRole("ROLE_RESTAURANT")&&restaurantManager.getDishes(name).contains(restaurantManager.getDish(id2))){
            restaurantManager.updateDish(id2,newDish);
            return new ResponseEntity<>(restaurantManager.getDish(id2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //Delete the dish (ID2) of the restaurant catalog (ID1)
    @DeleteMapping("/deleteDish/{id2}")
    public ResponseEntity<Dishes> deleteDish(HttpServletRequest request,@PathVariable long id2) {
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        if((request.isUserInRole("ROLE_RESTAURANT")&&restaurantManager.getDishes(name).contains(restaurantManager.getDish(id2)))||request.isUserInRole("ROLE_ADMIN")){
            restaurantManager.removeDish(id2);
            return new ResponseEntity<>(restaurantManager.getDish(id2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/restaurant/{nameRest}/dishes/search/{name}")
    public Collection<Dishes> dishesSearch(@PathVariable String nameRest, @PathVariable String name){
        return restaurantManager.findDishByName(restaurantManager.getRestaurant(nameRest),name);
    }

    @GetMapping("/restaurant/{name}/dishes/filter")
    public Collection<Dishes> dishesfilter(@PathVariable String name, @RequestParam int min,@RequestParam int max, @RequestParam List<String> type){
        return switch (type.size()) {
            case 1 -> restaurantManager.findByPriceRangeAndType(min, max, type.get(0), restaurantManager.getRestaurant(name));
            case 2 -> restaurantManager.findByPriceRangeAndTwoTypes(min, max, type.get(0),type.get(1), restaurantManager.getRestaurant(name));
            default -> restaurantManager.findByPriceRange(min, max, restaurantManager.getRestaurant(name));
        };
    }
}

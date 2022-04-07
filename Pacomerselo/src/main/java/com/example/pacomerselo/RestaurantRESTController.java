package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurants(@PathVariable long id){
        Restaurant restaurant = restaurantManager.getRestaurant(id);
        if(restaurant != null){
            return new ResponseEntity<>(restaurant,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update the restaurant given (ID)
    @PutMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable long id, @RequestBody Restaurant newRestaurant) {
        Restaurant restaurant = restaurantManager.updateRestaurant(id,newRestaurant);
        if (restaurant!= null) {
            return new ResponseEntity<>(newRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the restaurant given (ID)
    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable long id) {
        Restaurant restaurant=restaurantManager.removeRestaurant(id);
        if(restaurant != null){
            return new ResponseEntity<>(restaurant,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //////////////////////DISHES REST CONTROLLER//////////////////////
    //Add a new dish to the restaurant catalog (given the ID)
    @PostMapping("/restaurant/{id}/dishes")
    public ResponseEntity<Dishes> newDish(@PathVariable long id,@RequestBody Dishes dish){
        Restaurant restaurant = restaurantManager.getRestaurant(id);
        if (restaurant!= null) {
            restaurantManager.addDish(id,dish);
            return new ResponseEntity<>(dish, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Get all the dishes of the restaurant catalog (given the ID)
    @GetMapping("/restaurant/{id}/dishes")
    public Collection<Dishes> getDishes(@PathVariable long id){
        return restaurantManager.getDishes(id);
    }

    //Get the dish (ID2) of the restaurant catalog (ID1)
    @GetMapping("/restaurant/{id1}/dishes/{id2}")
    public ResponseEntity<Dishes> getDish(@PathVariable long id1,@PathVariable long id2){
        Dishes dish = restaurantManager.getDish(id2);
        if(dish != null){
            return new ResponseEntity<>(dish,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update the dish (ID2) of the restaurant catalog (ID1)
    @PutMapping("/restaurant/{id1}/dishes/{id2}")
    public ResponseEntity<Dishes> updateDish(@PathVariable long id1, @PathVariable long id2, @RequestBody Dishes newDish) {
        Dishes dish=restaurantManager.getDish(id2);
        if (dish!= null) {
            restaurantManager.updateDish(id2,newDish);
            return new ResponseEntity<>(newDish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the dish (ID2) of the restaurant catalog (ID1)
    @DeleteMapping("/restaurant/{id1}/dishes/{id2}")
    public ResponseEntity<Dishes> deleteDish(@PathVariable long id1,@PathVariable long id2) {
        Dishes dish= restaurantManager.removeDish(id2);
        if(dish != null){
            return new ResponseEntity<>(dish,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

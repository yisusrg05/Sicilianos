package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api")
@RestController
public class RESTController {

    @Autowired
    RestaurantHolder restaurantHolder;

    //////////////////////RESTAURANTS REST CONTROLLER//////////////////////
    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> newRestaurant(@RequestBody Restaurant restaurant){
        restaurantHolder.addRestaurant(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant")
    public Collection<Restaurant> getRestaurants(){
        return restaurantHolder.getRestaurants();
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurants(@PathVariable long id){
        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        if(restaurant != null){
            return new ResponseEntity<>(restaurant,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable long id, @RequestBody Restaurant newRestaurant) {
        Restaurant oldRestaurant = restaurantHolder.getRestaurant(id);
        if (oldRestaurant!= null) {
            newRestaurant.setId(id);
            newRestaurant.setDishes(oldRestaurant.getDishes());
            restaurantHolder.updateRestaurant(id,newRestaurant);
            return new ResponseEntity<>(newRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable long id) {
        Restaurant restaurant = restaurantHolder.removeRestaurant(id);
        if(restaurant != null){
            return new ResponseEntity<>(restaurant,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //////////////////////DISHES REST CONTROLLER//////////////////////
    @PostMapping("/restaurant/{id}/dishes")
    public ResponseEntity<Dishes> newDish(@PathVariable long id,@RequestBody Dishes dish){
        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        if (restaurant!= null) {
            restaurantHolder.addDish(id,dish);
            return new ResponseEntity<>(dish, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restaurant/{id}/dishes")
    public Collection<Dishes> getDishes(@PathVariable long id){
        return restaurantHolder.getDishes(id);
    }

    @GetMapping("/restaurant/{id1}/dishes/{id2}")
    public ResponseEntity<Dishes> getDish(@PathVariable long id1,@PathVariable long id2){
        Dishes dish = restaurantHolder.getDish(id1,id2);
        if(dish != null){
            return new ResponseEntity<>(dish,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/restaurant/{id1}/dishes/{id2}")
    public ResponseEntity<Dishes> updateDish(@PathVariable long id1, @PathVariable long id2, @RequestBody Dishes newDish) {
        Dishes oldDish=restaurantHolder.getDish(id1,id2);
        if (oldDish!= null) {
            newDish.setId(id2);
            restaurantHolder.updateDish(id1,id2,newDish);
            return new ResponseEntity<>(newDish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/restaurant/{id1}/dishes/{id2}")
    public ResponseEntity<Dishes> deleteDish(@PathVariable long id1,@PathVariable long id2) {
        Dishes dish= restaurantHolder.removeDish(id1,id2);
        if(dish != null){
            return new ResponseEntity<>(dish,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

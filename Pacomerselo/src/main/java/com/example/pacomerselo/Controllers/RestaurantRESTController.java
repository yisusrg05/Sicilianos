package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Managers.RestaurantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        int restaurantsDeleted= restaurantManager.removeRestaurant(id);
        if(restaurantsDeleted==1){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restaurant/search/{name}")
    public Collection<Restaurant> restaurantSearch(@PathVariable String name){
        return restaurantManager.findRestaurantByName(name);
    }

    //////////////////////DISHES REST CONTROLLER//////////////////////
    //Add a new dish to the restaurant catalog (given the ID)
    @PostMapping("/restaurant/{id}/dishes")
    public ResponseEntity<Dishes> newDish(@PathVariable long id, @RequestBody Dishes dish){
        Dishes dishAdded = restaurantManager.addDish(id,dish);
        if (dishAdded!= null) {
            return new ResponseEntity<>(dishAdded, HttpStatus.CREATED);
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
        int dishesDeleted= restaurantManager.removeDish(id2);
        if(dishesDeleted==1){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restaurant/{id}/dishes/search/{name}")
    public Collection<Dishes> dishesSearch(@PathVariable long id, @PathVariable String name){
        return restaurantManager.findDishByName(restaurantManager.getRestaurant(id),name);
    }

    @GetMapping("/restaurant/{id}/dishes/filter")
    public Collection<Dishes> dishesfilter(@PathVariable long id, @RequestParam int min,@RequestParam int max, @RequestParam List<String> type){
        return switch (type.size()) {
            case 1 -> restaurantManager.findByPriceRangeAndType(min, max, type.get(0), restaurantManager.getRestaurant(id));
            case 2 -> restaurantManager.findByPriceRangeAndTwoTypes(min, max, type.get(0),type.get(1), restaurantManager.getRestaurant(id));
            default -> restaurantManager.findByPriceRange(min, max, restaurantManager.getRestaurant(id));
        };
    }
}

package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Managers.RestaurantManager;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
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

    private final PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    //////////////////////RESTAURANTS REST CONTROLLER//////////////////////

    //Add a new Restaurant to the catalog
    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> newRestaurant(@RequestBody Restaurant restaurant){
        restaurant.setDescription(policy.sanitize(restaurant.getDescription()));
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
    @PutMapping("/restaurant/{name}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String name, @RequestBody Restaurant newRestaurant) {
        newRestaurant.setDescription(policy.sanitize(newRestaurant.getDescription()));
        Restaurant restaurant = restaurantManager.updateRestaurant(name,newRestaurant);
        if (restaurant!= null) {
            return new ResponseEntity<>(newRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the restaurant given (ID)
    @DeleteMapping("/restaurant/{name}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable String name) {
        Restaurant restaurant = new Restaurant();
        if (restaurant!= null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restaurant/search/{name}")
    public Collection<Restaurant> restaurantSearch(@PathVariable String name){
        return restaurantManager.findRestaurantByName(name);
    }

    //////////////////////DISHES REST CONTROLLER//////////////////////
    //Add a new dish to the restaurant catalog (given the ID)
    @PostMapping("/restaurant/{name}/dishes")
    public ResponseEntity<Dishes> newDish(@PathVariable String name, @RequestBody Dishes dish){
        dish.setDescription(policy.sanitize(dish.getDescription()));
        Dishes dishAdded = restaurantManager.addDish(name,dish);
        if (dishAdded!= null) {
            return new ResponseEntity<>(dishAdded, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    @PutMapping("/restaurant/{name}/dishes/{id2}")
    public ResponseEntity<Dishes> updateDish(@PathVariable String name, @PathVariable long id2, @RequestBody Dishes newDish) {
        newDish.setDescription(policy.sanitize(newDish.getDescription()));
        Dishes dish=restaurantManager.getDish(id2);
        if (dish!= null) {
            restaurantManager.updateDish(id2,newDish);
            return new ResponseEntity<>(newDish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the dish (ID2) of the restaurant catalog (ID1)
    @DeleteMapping("/restaurant/{name}/dishes/{id2}")
    public ResponseEntity<Dishes> deleteDish(@PathVariable String name,@PathVariable long id2) {
        int dishesDeleted= restaurantManager.removeDish(id2);
        if(dishesDeleted==1){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

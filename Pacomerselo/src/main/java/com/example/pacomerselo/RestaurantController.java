package com.example.pacomerselo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@Controller
public class RestaurantController {

    @Autowired
    RestaurantManager restaurantManager;

    //Get all the restaurants available
    @GetMapping("/restaurant")
    public String restaurant(Model model){
        Collection<Restaurant> restaurants=restaurantManager.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "pricing";
    }

    //Get a single restaurant, given the ID
    @GetMapping("/restaurant/{id1}")
    public String restaurantId(Model model, @PathVariable long id1){
        Collection<Dishes> dishes = restaurantManager.getDishes(id1);
        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        return "catalog-page";
    }

    //Add a new restaurant
    @PostMapping("/restaurant")
    public String addRestaurant (Model model, Restaurant restaurant){
        restaurantManager.addRestaurant(restaurant);
        Collection<Restaurant> restaurants =restaurantManager.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "pricing";
    }

    //Add a new dish to the restaurant given(
    @PostMapping("/restaurant/{id}")
    public String addDish (Model model, @PathVariable long id, Dishes dish){
        restaurantManager.addDish(id,dish);
        Collection<Dishes> dishes = restaurantManager.getDishes(id);
        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id);
        return "catalog-page";
    }

    //Update an already existing dish (ID2) from a given restaurant (ID1)
    @PostMapping("/restaurant/{id1}/updateDish/{id2}")
    public String updateDish(Model model, @PathVariable long id1, @PathVariable long id2, Dishes newDish) {

        restaurantManager.updateDish(id2,newDish);
        Collection<Dishes> dishes = restaurantManager.getDishes(id1);

        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        model.addAttribute("id2",id2);

        return "updateDishSuccessful";
    }

    //Delete an already existing dish (ID2) from a given restaurant (ID1)
    @GetMapping("/restaurant/{id1}/deleteDish/{id2}")
    public String deleteDish (Model model, @PathVariable long id1, @PathVariable long id2){
        restaurantManager.removeDish(id2);
        Collection<Dishes> dishes = restaurantManager.getDishes(id1);

        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        model.addAttribute("id2",id2);

        return "deleteDishSuccessful";
    }

    //Update an already existing restaurant given the ID
    @PostMapping("/updateRestaurant/{id}")
    public String putRestaurant (Model model,@PathVariable long id,Restaurant newRestaurant){
        restaurantManager.updateRestaurant(id,newRestaurant);
        Collection<Restaurant> restaurants =restaurantManager.getRestaurants();

        model.addAttribute("restaurants",restaurants);
        return "updateSuccesful";
    }

    //Delete an already existing restaurant given the ID
    @GetMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant (Model model, @PathVariable long id){
        restaurantManager.removeRestaurant(id);
        Collection<Restaurant> restaurants =restaurantManager.getRestaurants();

        model.addAttribute("restaurants",restaurants);

        return "deleteRestaurantSuccessful";
    }

    @GetMapping("/nosotros")
    public String nosotros(){return "about-us";}

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/reviews")
    public String reviews(){
        return "reviews";
    }

    @GetMapping("/updateSuccesful")
    public String updateSuccessful(){
        return "updateSuccesful";
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

    @GetMapping("/{id}/registerDish")
    public String registerDish(Model model, @PathVariable long id){
        model.addAttribute("id",id);
        return "registrationDish";
    }

    @GetMapping ("/registerRestaurant")
    public String registerRestaurant(){
        return "registrationRest";
    }

    //Giving the needed information to update a restaurant
    @GetMapping ("/updateRestaurant/{id}")
    public String updateRestaurant(Model model, @PathVariable long id){
        Restaurant restaurant = restaurantManager.getRestaurant(id);

        model.addAttribute("id",id);
        model.addAttribute("restaurant",restaurant);

        return "updateRest";
    }

    //Giving the needed information to update a dish
    @GetMapping ("/restaurant/{id1}/updateDish/{id2}")
    public String updateDishes(Model model, @PathVariable long id1 /*Restaurant ID*/, @PathVariable long id2/*Dish ID*/){
        Dishes dish = restaurantManager.getDish(id2);

        model.addAttribute("id1",id1);
        model.addAttribute("id2",id2);
        model.addAttribute("dish",dish);

        return "updateDish";
    }

    @GetMapping("/faq")
    public String faq(){
        return "faq";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/forgottenPassword")
    public String forgottenPassword(){
        return "forgottenPassword";
    }
}
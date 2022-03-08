package com.example.pacomerselo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class Restaurantes_Controller {

    @Autowired
    RestaurantHolder restaurantHolder;
    /*public Restaurantes_Controller(){
        restaurantHolder.addRestaurant(new Restaurant("Sicilia","come mucho"));
        restaurantHolder.addRestaurant(new Restaurant("Jorge","ahora es vegano"));
    }*/
    @GetMapping("/restaurant")
    public String restaurant(Model model){
        Collection<Restaurant> restaurants =restaurantHolder.getRestaurants();
        model.addAttribute("restaurants",restaurants);

        return "pricing";
    }
    @GetMapping("/restaurant/{id}")
    public String restaurantId(Model model, @PathVariable long id){
        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        Collection<Dishes> dishes = restaurant.allDishes();

        model.addAttribute("dishes",dishes);
        model.addAttribute("id",id);

        return "catalog-page";
    }
    @PostMapping("/restaurant")
    public String addRestaurant (Model model, Restaurant restaurant){
        restaurantHolder.addRestaurant(restaurant);
        Collection<Restaurant> restaurants =restaurantHolder.getRestaurants();
        model.addAttribute("restaurants",restaurants);

        return "pricing";
    }

    @PostMapping("/restaurant/{id}")
    public String addDish (Model model, @PathVariable long id, Dishes dish){
        restaurantHolder.addDish(id,dish);
        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        Collection<Dishes> dishes = restaurant.allDishes();
        model.addAttribute("dishes",dishes);
        return "catalog-page";
    }

    @PutMapping("/restaurant/{id}")
    public String putRestaurant (Model model,@RequestBody Restaurant newRestaurant, @PathVariable long id){
        Restaurant oldRestaurant = restaurantHolder.getRestaurant(id);

        restaurantHolder.updateRestaurant(oldRestaurant.getId(), newRestaurant);
        model.addAttribute("restaurants",newRestaurant);

        return "pricing";
    }

    @DeleteMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant (Model model,Restaurant restaurant, @PathVariable long id){
        restaurantHolder.getRestaurant(id);
        restaurantHolder.removeRestaurant(id);
        Collection<Restaurant> restaurants =restaurantHolder.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "pricing";
    }






    @GetMapping("/nosotros")
    public String nosotros(){

        return "about-us";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/catalogo")
    public String catalog(){
        return "catalog-page";
    }
    @GetMapping("/reviews")
    public String reviews(){
        return "reviews";
    }
    @GetMapping("/payment")
    public String payment(){
        return "payment-page";
    }
    @GetMapping("/register")
    public String register(){
        return "registration";
    }
    @GetMapping("/{id}/registerDish")
    public String registerDish(Model model, @PathVariable long id){
        Restaurant restaurant= restaurantHolder.getRestaurant(id);
        model.addAttribute("id",id);
        return "registrationDish";
    }
    @GetMapping ("/registerRestaurant")
    public String registerRestaurant(){
        return "registrationRest";
    }
    @GetMapping ("/updateRestaurant/{id}")
    public String updateRestaurant(Model model, @PathVariable long id){
        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        model.addAttribute("id",id);
        return "updateRest";
    }
    @GetMapping("/faq")
    public String faq(){
        return "faq";
    }
    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/forgottenPassword")
    public String forgottenPassword(){
        return "forgottenPassword";
    }
    @GetMapping("/orderPlaced")
    public String orderPlaced(){
        return "orderPlaced";
    }



}

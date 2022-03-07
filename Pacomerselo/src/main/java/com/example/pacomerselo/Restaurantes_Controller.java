package com.example.pacomerselo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<Restaurant> restaurants = (List<Restaurant>) restaurantHolder.getRestaurants();

        model.addAttribute("restaurants",restaurants);

        return "list";
    }
    @GetMapping("/restaurant/{id}")
    public String restaurantId(Model model, @PathVariable int id){

        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        List<Dishes> list = (List <Dishes>)restaurant.allDishes();

        model.addAttribute("list",list);

        return "catalog-page";
    }
    @PostMapping("/restaurant/new")
    public String addRestaurant (Model model, Restaurant restaurant){

        restaurantHolder.addRestaurant(restaurant);

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

}

package com.example.pacomerselo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Restaurantes_Controller {

    @Autowired
    RestaurantHolder restaurantHolder;

    @GetMapping("/restaurant")
    public String restaurant(Model model){

        List<Restaurant> restaurants = (List<Restaurant>) restaurantHolder.getRestaurants();

        model.addAttribute("restaurant",restaurants);

        return "pricing";
    }
    @GetMapping("/restaurant/{id}")
    public String restaurantId(Model model, @PathVariable int id){

        Restaurant restaurant = restaurantHolder.getRestaurant(id);

        model.addAttribute("restaurant",restaurant);

        return "catalog-page";
    }

    @PostMapping("/restaurant/new")
    public String addRestaurant (Model model, Restaurant restaurant){

        restaurantHolder.addRestaurant(restaurant);

        return "pricing";
    }

}

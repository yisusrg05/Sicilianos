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
    public String restaurantId(Model model, @PathVariable int id){

        Restaurant restaurant = restaurantHolder.getRestaurant(id);
        Collection<Dishes> list = restaurant.allDishes();

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
    @GetMapping("/carrito")
    public String shoppingCart(){
        return "shopping-cart";
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
    @GetMapping("/registerDish")
    public String registerDish(){
        return "registrationDish";
    }
    @GetMapping("/registerRestaurant")
    public String registerRestaurant(){
        return "registrationRest";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }



}

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

    @PostMapping("/updateRestaurant/{id}")
    public String putRestaurant (Model model,@PathVariable long id,Restaurant newRestaurant){
        newRestaurant.setId(id);
        newRestaurant.setDishes(restaurantHolder.getRestaurant(id).getDishes());
        restaurantHolder.updateRestaurant(id, newRestaurant);
        Collection<Restaurant> restaurants =restaurantHolder.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "updateSuccesful";
    }

    @GetMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant (Model model, @PathVariable long id){
        restaurantHolder.removeRestaurant(id);
        Collection<Restaurant> restaurants =restaurantHolder.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "deleteSuccesful";
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
    @GetMapping("/updateSuccesful")
    public String okkkkkkkk(){
        return "updateSuccesful";
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

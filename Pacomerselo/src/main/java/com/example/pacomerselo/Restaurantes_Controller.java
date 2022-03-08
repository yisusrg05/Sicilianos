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
    UserHolder userHolder;

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
        Restaurant restaurant= restaurantHolder.getRestaurant(id);
        long idDish= dish.getId();
        restaurant.add(idDish,dish);
        Collection<Dishes> dishes = restaurant.allDishes();
        model.addAttribute("dishes",dishes);

        return "catalog-page";
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
    public String shoppingCart(Model model){
        User user= userHolder.getUser(1);
        Collection<Dishes> shopping = user.allCart();
        model.addAttribute("cart",shopping);
        int comida=0;
        for(Dishes dish : shopping){
            comida+=dish.getPrice();
        }
        int total=5+comida;
        model.addAttribute("comida",comida);
        model.addAttribute("total",total);
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





    @GetMapping("/index")
    public String index(){
        return "index";
    }



}

package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class UserController extends RestaurantHolder{

    @Autowired
    UserHolder userHolder;
    @Autowired
    RestaurantHolder restaurantHolder;

    @GetMapping("/carrito")
    public String shoppingCart(Model model){
        User user= userHolder.getUser(1);
        boolean vacio= !user.getCart().isEmpty();
        boolean mostrarAhora=!vacio;
        model.addAttribute("vacio",vacio);
        model.addAttribute("now",mostrarAhora);
        return getString(model);
    }

    @GetMapping("/addcarrito/{id1}/{id2}")
    public String addShoppingCart(@PathVariable long id1, @PathVariable long id2){
        Dishes dish= restaurantHolder.getDish(id1,id2);
        userHolder.addDishToCart(1,dish);
        return "updateSuccesful";
    }

    @GetMapping("/deletecart/{id1}/{id2}")
    public String deleteShoppingCart(Model model,@PathVariable long id1, @PathVariable long id2){
        Dishes dish= restaurantHolder.getDish(id1,id2);
        userHolder.deleteDishFromCart(1,dish);
        return "deleteSuccesful";
    }

    @GetMapping("/deleteCart")
    public String deleteCart(Model model){
       userHolder.deleteCart(1);
       return "deleteSuccesful";
    }


    @PostMapping("/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password){
        boolean valid= userHolder.validUser(username,password);
        boolean error= !(userHolder.validUser(username,password));
        model.addAttribute("ok",valid);
        model.addAttribute("error",error);

        return "loginResult";
    }

    @GetMapping("/payment")
    public String payment(Model model){
        User user= userHolder.getUser(1);
        Collection<Dishes> cart = user.allCart();
        model.addAttribute("cart",cart);
        int total=5+foodCart();
        model.addAttribute("total",total);
        return "payment-page";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        User user= userHolder.getUser(1);
        model.addAttribute("user",user);
        model.addAttribute("order",user.getOrders().values());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Model model, User newUser){
        newUser.setId(1);
        newUser.setCart(userHolder.getDishes(1));
        newUser.setOrders(userHolder.getOrders(1));
        userHolder.updateUser(1,newUser);
        model.addAttribute("order",newUser.getOrders().values());
        model.addAttribute("user",newUser);
        return "profile";
    }

    @PostMapping("/forgottenPassword")
    public String updatePassword(@RequestParam String username,@RequestParam String email, @RequestParam String password){
        User user=userHolder.getUser(username);
        if(user!=null&&user.getEmail().equals(email)){
            user.setPassword(password);
        }
        assert user != null;
        userHolder.updateUser(user.getId(),user);
        return "login";
    }

    @GetMapping("/orderPlaced")
    public String placeOrder(){
        userHolder.proccessOrder(1);
        return "orderPlaced";
    }

    @PostMapping("/register")
    public String addUser(User newUser){
        userHolder.addUser(newUser);
        return "updateSuccesful";
    }

    private int foodCart(){
        User user= userHolder.getUser(1);
        Collection<Dishes> cart = user.allCart();
        int comida=0;
        for(Dishes dish : cart){
            comida+=dish.getPrice();
        }
        return comida;
    }

    private String getString(Model model) {
        Collection<Dishes> cart = userHolder.getUser(1).allCart();
        model.addAttribute("cart",cart);
        int comida=foodCart();
        int total=5+comida;
        model.addAttribute("comida",comida);
        model.addAttribute("total",total);
        return "cart";
    }
}

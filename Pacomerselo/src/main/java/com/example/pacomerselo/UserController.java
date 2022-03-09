package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class UserController {

    @Autowired
    UserHolder userHolder;

    @GetMapping("/carrito")
    public String shoppingCart(Model model){
        User user= userHolder.getUser(1);
        Collection<Dishes> cart = user.allCart();
        model.addAttribute("cart",cart);
        int comida=foodCart();
        int total=5+comida;
        model.addAttribute("comida",comida);
        model.addAttribute("total",total);
        return "shopping-cart";
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
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Model model, User newUser){
        newUser.setId(1);
        newUser.setCart(userHolder.getDishes(1));
        userHolder.updateUser(1,newUser);
        model.addAttribute("user",newUser);
        return "profile";
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
}

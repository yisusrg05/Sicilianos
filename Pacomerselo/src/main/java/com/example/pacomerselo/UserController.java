package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        int comida=0;
        for(Dishes dish : cart){
            comida+=dish.getPrice();
        }
        int total=5+comida;
        model.addAttribute("comida",comida);
        model.addAttribute("total",total);
        return "shopping-cart";
    }
}

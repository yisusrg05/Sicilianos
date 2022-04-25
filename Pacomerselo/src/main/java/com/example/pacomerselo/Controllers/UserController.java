package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserManager userManager;

    /*
    //Get the cart
    @GetMapping("/carrito")
    public String shoppingCart(Model model){
        User user= userHolder.getUser(1);
        boolean vacio= !user.getCart().isEmpty(); //Boolean for the HTML
        boolean mostrarAhora=!vacio; //Another Boolean for the HTML
        //If cart is empty, the HTML showed is a bit different from the used cart,
        //, so it depends on the result of this booleans

        model.addAttribute("vacio",vacio);
        model.addAttribute("now",mostrarAhora);

        return getCart(model);//Calling a private function in order to not repeat code
    }

    //Adding a new dish to the cart, given its id (ID1) and the restaurant id (ID2)
    @GetMapping("/addcarrito/{id1}/{id2}")
    public String addShoppingCart(@PathVariable long id1, @PathVariable long id2){
        Dishes dish= restaurantManager.getDish(id2);
        userHolder.addDishToCart(1,dish);

        return "addToCartSuccessful";

    }

    //Deleting a new dish from the cart, given its id (ID1) and the restaurant id (ID2)
    @GetMapping("/deletecart/{id1}/{id2}")
    public String deleteShoppingCart(Model model,@PathVariable long id1, @PathVariable long id2){
        Dishes dish= restaurantManager.getDish(id2);
        userHolder.deleteDishFromCart(1,dish);

        return "deleteCartSuccssesful";
    }

    //Delete all the cart
    @GetMapping("/deleteCart")
    public String deleteCart(Model model){
       userHolder.deleteCart(1);

       return "deleteCartSuccssesful";
    }*/


    /*
    //Allows login, getting the username and password as Parameters
    // , and showing diferent results in the HTML depending on the login result
    @PostMapping("/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password){
        boolean valid= userHolder.validUser(username,password);
        boolean error= !(userHolder.validUser(username,password));

        model.addAttribute("ok",valid); //Login valid
        model.addAttribute("error",error); //Login invalid

        return "loginResult";
    }

     */
    /*
    //Get the checkout page, showing a little summary of the cart
    @GetMapping("/payment")
    public String payment(Model model){
        User user= userHolder.getUser(1);
        Collection<Dishes> cart = user.allCart();

        model.addAttribute("cart",cart);

        int total=5+foodCart();
        model.addAttribute("total",total);

        return "payment-page";
    }*/

    //Get the profile information (personal data and orders)
    @GetMapping("/profile")
    public String profile(Model model){
        User user= userManager.getUser(24);
        model.addAttribute("user",user);
        model.addAttribute("order", userManager.findOrdersByUser(user));

        return "profile";
    }

    //Updating the profile, except the password
    @PostMapping("/profile")
    public String updateProfile(Model model, User newUser){
        userManager.updateUser(24,newUser);
        User user= userManager.getUser(24);
        model.addAttribute("order",userManager.findOrdersByUser(user));
        model.addAttribute("user",user);
        return "profile";
    }

    //Update just the password, not all the User
    @PostMapping("/forgottenPassword")
    public String updatePassword(@RequestParam String username,@RequestParam String email, @RequestParam String password){
        List<User> user= userManager.findByUsernameAndEmail(username,email);
        if(!user.isEmpty()){
            userManager.updateUserPassword(user.get(0),password);
            return "login";
        }else{
            return "incorrectEmailOrPassword";
        }
    }


    /*
    @GetMapping("/orderPlaced")
    public String placeOrder(){
        userManager.proccessOrder(1);
        return "orderPlaced";
    }
    */

    @PostMapping("/register")
    public String addUser(User newUser){
        if(userManager.findByUsername(newUser.getUsername()).isEmpty()){
            userManager.addUser(newUser);
            return "registerSuccessful";
        }
        else{
            return "alreadyExistingUser";
        }
    }
    /*
    //Private function thet calculates all the price of the cart, excluding the shipping taxes
    private int foodCart(){
        User user= userHolder.getUser(1);
        Collection<Dishes> cart = user.allCart();
        int comida=0;
        for(Dishes dish : cart){
            comida+=dish.getPrice();
        }
        return comida;
    }


    //Private string that shows the cart in the web App when called
    private String getCart(Model model) {
        Collection<Dishes> cart = userHolder.getUser(1).allCart();
        model.addAttribute("cart",cart);
        int comida=foodCart();
        int total=5+comida;

        model.addAttribute("comida",comida);
        model.addAttribute("total",total);

        return "cart";
    }*/
}

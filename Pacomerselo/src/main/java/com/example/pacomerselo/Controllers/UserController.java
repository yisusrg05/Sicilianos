package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.SessionCart;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Managers.RestaurantManager;
import com.example.pacomerselo.Managers.UserManager;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestaurantManager restaurantManager;
    @Autowired
    private SessionCart sessionCart;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());

        return "login";
    }


    //Get the cart
    @GetMapping("/cart")
    public String shoppingCart(Model model, HttpServletRequest request,HttpSession httpSession){
        this.sessionCart= (SessionCart) httpSession.getAttribute("cart");
        boolean vacio=this.sessionCart.getCart().isEmpty();
        model.addAttribute("cart",sessionCart);
        model.addAttribute("vacio",vacio);

        return "cart";//Calling a private function in order to not repeat code
    }


    //Adding a new dish to the cart, given its id (ID1) and the restaurant id (ID2)
    @GetMapping("/addcarrito/{id1}")
    public String addShoppingCart(@PathVariable long id1, HttpSession httpSession){
        this.sessionCart= (SessionCart) httpSession.getAttribute("cart");
        this.sessionCart.add(restaurantManager.getDish(id1));
        httpSession.setAttribute("cart",this.sessionCart);
        return "addToCartSuccessful";

    }


    //Deleting a new dish from the cart, given its id (ID1) and the restaurant id (ID2)
    @GetMapping("/deletecart/{id}")
    public String deleteShoppingCart(Model model,HttpSession httpSession,@PathVariable long id){

        this.sessionCart=(SessionCart) httpSession.getAttribute("cart");
        this.sessionCart.deleteDish(id);
        httpSession.setAttribute("cart",this.sessionCart);

        return "deleteCartSuccssesful";
    }


    //Delete all the cart
    @GetMapping("/deleteCart")
    public String deleteCart(Model model,HttpSession httpSession){
       httpSession.setAttribute("cart",this.sessionCart= new SessionCart());
       return "deleteCartSuccssesful";
    }


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
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request,HttpSession httpSession){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userManager.findByUsername(username).orElse(null);
        this.sessionCart=new SessionCart();
        httpSession.setAttribute("cart",this.sessionCart);
        model.addAttribute("user",user);
        model.addAttribute("order", userManager.findOrdersByUser(user));

        return "profile";
    }

    //Updating the profile, except the password
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/profile")
    public String updateProfile(Model model, User newUser, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        User user= userManager.findByUsername(username).orElse(null);
        userManager.updateUser(username,newUser);
        model.addAttribute("order",userManager.findOrdersByUser(user));
        model.addAttribute("user",user);
        return "profile";
    }

    //Update just the password, not all the User
    @PostMapping("/forgottenPassword")
    public String updatePassword(@RequestParam String username,@RequestParam String email, @RequestParam String password){
        List<User> user= userManager.findByUsernameAndEmail(username,email);
        if(!user.isEmpty()){
            userManager.updateUserPassword(user.get(0),passwordEncoder.encode(password));
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
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
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

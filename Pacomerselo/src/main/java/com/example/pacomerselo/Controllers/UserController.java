package com.example.pacomerselo.Controllers;


import com.example.pacomerselo.Entities.SessionCart;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Managers.RestaurantManager;
import com.example.pacomerselo.Managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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
    private SessionCart sessionCart= new SessionCart();

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());

        return "login";
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model, HttpServletRequest request){
        HttpSession httpSession= request.getSession();
        this.sessionCart= (SessionCart) httpSession.getAttribute("cart");
        boolean vacio=this.sessionCart.getCart().isEmpty();
        model.addAttribute("cart",sessionCart);
        model.addAttribute("vacio",vacio);

        return userCustomization(model,request,"cart");
    }


    //Adding a new dish to the cart, given its id (ID1) and the restaurant id (ID2)
    @GetMapping("/addcarrito/{id1}")
    public String addShoppingCart(@PathVariable long id1, Model model, HttpServletRequest request){
        HttpSession httpSession=request.getSession();
        this.sessionCart= (SessionCart) httpSession.getAttribute("cart");
        this.sessionCart.add(restaurantManager.getDish(id1));
        httpSession.setAttribute("cart",this.sessionCart);
        String idRestaurant=restaurantManager.getDish(id1).getRestaurant().getName();
        model.addAttribute("id",idRestaurant);
        return userCustomization(model,request,"addToCartSuccessful");

    }


    //Deleting a new dish from the cart, given its id (ID1) and the restaurant id (ID2)
    @GetMapping("/deletecart/{id}")
    public String deleteShoppingCart(Model model,HttpServletRequest request, @PathVariable long id){
        HttpSession httpSession= request.getSession();
        this.sessionCart=(SessionCart) httpSession.getAttribute("cart");
        this.sessionCart.deleteDish(id);
        httpSession.setAttribute("cart",this.sessionCart);

        return userCustomization(model,request,"deleteCartSuccessful");
    }


    //Delete all the cart
    @GetMapping("/deleteCart")
    public String deleteCart(Model model,HttpServletRequest request){
        HttpSession httpSession=request.getSession();
        httpSession.setAttribute("cart",this.sessionCart= new SessionCart());
        return userCustomization(model,request,"deleteCartSuccessful");
    }



    //Get the checkout page, showing a little summary of the cart
    @GetMapping("/payment")
    public String payment(Model model, HttpServletRequest request){
        HttpSession httpSession=request.getSession();
        this.sessionCart= (SessionCart) httpSession.getAttribute("cart");
        model.addAttribute("cart",sessionCart);
        return userCustomization(model,request,"payment-page");
    }

    //Get the profile information (personal data and orders)
    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request){
        HttpSession httpSession=request.getSession();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userManager.findByUsername(username).orElse(null);
        if(httpSession.isNew()){
            this.sessionCart=new SessionCart();
        }
        httpSession.setAttribute("cart",this.sessionCart);
        model.addAttribute("user",user);
        model.addAttribute("order", userManager.findOrdersByUser(user));

        return userCustomization(model,request,"profile");
    }

    //Updating the profile, except the password
    @PostMapping("/profile")
    public String updateProfile(Model model, User newUser, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        User user= userManager.findByUsername(username).orElse(null);
        userManager.updateUser(username,newUser);
        model.addAttribute("order",userManager.findOrdersByUser(user));
        model.addAttribute("user",user);
        return userCustomization(model,request,"profile");

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

    @PostMapping("/orderPlaced")
    public String placeOrder(HttpServletRequest request, @RequestParam long finalPrice){
        HttpSession httpSession=request.getSession();
        this.sessionCart=(SessionCart) httpSession.getAttribute("cart");
        this.sessionCart.setTotal(finalPrice);
        userManager.proccessOrder(request.getUserPrincipal().getName(),this.sessionCart);
        this.sessionCart= new SessionCart();
        httpSession.setAttribute("cart",new SessionCart());
        return "orderPlaced";
    }

    @PostMapping("/register")
    public String addUser(User newUser){
        if(userManager.findByUsername(newUser.getUsername()).isEmpty()){
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            List<String> role= new ArrayList<>();
            role.add("USER");
            newUser.setRoles(role);
            userManager.addUser(newUser);
            return "registerSuccessful";
        }
        else{
            return "alreadyExistingUser";
        }
    }

    @PostMapping("/payment")
    public String applyDiscount(Model model, HttpServletRequest request, long newprice){
        HttpSession httpSession=request.getSession();
        this.sessionCart= (SessionCart) httpSession.getAttribute("cart");
        this.sessionCart.setTotal(newprice);
        httpSession.setAttribute("cart", this.sessionCart);
        model.addAttribute("cart",sessionCart);
        return userCustomization(model,request,"payment-page");
    }

    private String userCustomization(Model model, HttpServletRequest request, String page){
        boolean logged=false;
        boolean admin=false;
        if(SecurityContextHolder.getContext().getAuthentication()!=null&&request.isUserInRole("ROLE_USER")){
            String username=request.getUserPrincipal().getName();
            if(request.isUserInRole("ROLE_ADMIN")){
                admin=true;
            }
            logged=true;
            model.addAttribute("username",username);
        }
        model.addAttribute("logged",logged);
        model.addAttribute("admin",admin);
        return page;
    }
}

package com.example.pacomerselo.Controllers;

import com.example.pacomerselo.Entities.DishType;
import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Managers.RestaurantManager;
import com.example.pacomerselo.Repositories.Restaurant.RestaurantRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
public class RestaurantController {

    @Autowired
    RestaurantManager restaurantManager;
    @Autowired
    RestaurantRepositoryImpl restaurantRepository;


    //Get all the restaurants available
    @PreAuthorize("permitAll()")
    @GetMapping("/restaurant")
    public String restaurant(Model model, HttpServletRequest request){
        Collection<Restaurant> restaurants=restaurantManager.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        model.addAttribute("empty",restaurants.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"pricing");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/restaurant/search")
    public String restaurantSearch(Model model, HttpServletRequest request, @RequestParam String name){
        Collection<Restaurant> restaurants=restaurantManager.findRestaurantByName(name);
        model.addAttribute("restaurants",restaurants);
        model.addAttribute("empty",restaurants.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"pricing");
    }

    //Get a single restaurant, given the ID
    @PreAuthorize("permitAll()")
    @GetMapping("/restaurant/{id1}")
    public String restaurantId(Model model, HttpServletRequest request, @PathVariable long id1){
        Collection<Dishes> dishes = restaurantManager.getDishes(id1);
        List<String> list= DishType.DESSERT.types();

        model.addAttribute("types", list);
        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        model.addAttribute("empty",dishes.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"catalog-page");    }

    @PreAuthorize("permitAll()")
    @PostMapping("/restaurant/{id1}/search")
    public String restaurantDishesSearch(Model model, HttpServletRequest request, @PathVariable long id1, @RequestParam String name){
        Restaurant restaurant=restaurantManager.getRestaurant(id1);
        Collection<Dishes> dishes = restaurantManager.findDishByName(restaurant,name);
        List<String> list= DishType.DESSERT.types();

        model.addAttribute("types", list);
        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        model.addAttribute("empty",dishes.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"catalog-page");
    }

    //Add a new restaurant
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/restaurant")
    public String addRestaurant (Model model, HttpServletRequest request, Restaurant restaurant){
        restaurantManager.addRestaurant(restaurant);
        Collection<Restaurant> restaurants =restaurantManager.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        model.addAttribute("empty",restaurants.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"pricing");
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/restaurant/{id}")
    public String addDish (Model model, HttpServletRequest request, @PathVariable long id, Dishes dish){
        dish.setRestaurant(restaurantManager.getRestaurant(id));
        restaurantManager.addDish(id,dish);

        Collection<Dishes> dishes = restaurantManager.getDishes(id);
        List<String> list= DishType.DESSERT.types();

        model.addAttribute("types", list);
        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id);
        model.addAttribute("filter", false);
        model.addAttribute("minimun",0);
        model.addAttribute("maximum",0);
        model.addAttribute("type",0);
        model.addAttribute("empty",dishes.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"catalog-page");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/restaurant/{id}/filter")
    public String filterDish (Model model, HttpServletRequest request, @PathVariable long id, @RequestParam int min,@RequestParam int max, @RequestParam List<String> type){
        Restaurant restaurant=restaurantManager.getRestaurant(id);
        List<Dishes> dishes = switch (type.size()) {
            case 2 -> restaurantManager.findByPriceRangeAndType(min, max, type.get(0), restaurant);
            case 3 -> restaurantManager.findByPriceRangeAndTwoTypes(min, max, type.get(0),type.get(1), restaurant);
            default -> restaurantManager.findByPriceRange(min, max, restaurant);
        };
        List<String> list= DishType.DESSERT.types();
        String filter=toStringFilter(type,min,max);

        model.addAttribute("types", list);
        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id);
        model.addAttribute("filter", true);
        model.addAttribute("type",type);
        model.addAttribute("stringFilter",filter);
        model.addAttribute("empty",dishes.isEmpty());
        model.addAttribute("stringEmpty","Tu búsqueda no ha arrojado ningún resultado");
        return userCustomization(model,request,"catalog-page");
    }


    private String toStringFilter(List<String> lista, int min, int max){
        StringBuilder sb = new StringBuilder();
        if(lista.size()==3){
            sb.append("Platos de los tipos ").append(lista.get(0)).append(" y ").append(lista.get(1)).append(" en el intervalo de ").append(min).append("€ a ").append(max).append("€");
        }else if(lista.size()==2){
            sb.append("Platos del tipo ").append(lista.get(0)).append(" en el intervalo de ").append(min).append("€ a ").append(max).append("€");

        }else{
            sb.append("Platos de todos los tipos en el intervalo de ").append(min).append("€ a ").append(max).append("€");
        }
        return sb.toString();
    }

    //Update an already existing dish (ID2) from a given restaurant (ID1)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/restaurant/{id1}/updateDish/{id2}")
    public String updateDish(Model model, HttpServletRequest request, @PathVariable long id1, @PathVariable long id2, Dishes newDish) {
        restaurantManager.updateDish(id2,newDish);
        Collection<Dishes> dishes = restaurantManager.getDishes(id1);

        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        model.addAttribute("id2",id2);

        return userCustomization(model,request,"updateDishSuccessful");
    }

    //Delete an already existing dish (ID2) from a given restaurant (ID1)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurant/{id1}/deleteDish/{id2}")
    public String deleteDish (Model model, HttpServletRequest request, @PathVariable long id1, @PathVariable long id2){
        restaurantManager.removeDish(id2);
        Collection<Dishes> dishes = restaurantManager.getDishes(id1);

        model.addAttribute("dishes",dishes);
        model.addAttribute("id1",id1);
        model.addAttribute("id2",id2);

        return userCustomization(model,request,"deleteDishSuccessful");
    }

    //Update an already existing restaurant given the ID
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/updateRestaurant/{id}")
    public String putRestaurant (Model model, HttpServletRequest request,@PathVariable long id,Restaurant newRestaurant){
        restaurantManager.updateRestaurant(id,newRestaurant);
        Collection<Restaurant> restaurants =restaurantManager.getRestaurants();

        model.addAttribute("restaurants",restaurants);
        return userCustomization(model,request,"updateSuccessful");
    }

    //Delete an already existing restaurant given the ID
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant (Model model, HttpServletRequest request, @PathVariable long id){
        restaurantManager.removeRestaurant(id);
        Collection<Restaurant> restaurants =restaurantManager.getRestaurants();

        model.addAttribute("restaurants",restaurants);

        return userCustomization(model,request,"deleteRestaurantSuccessful");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/nosotros")
    public String nosotros( Model model, HttpServletRequest request) {
        return userCustomization(model,request,"about-us");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/accessDenied")
    public String accessDenied( HttpServletRequest request){
        return "accessDenied";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/reviews")
    public String reviews(Model model, HttpServletRequest request){
        return userCustomization(model,request,"reviews");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/updateSuccesful")
    public String updateSuccessful( Model model,HttpServletRequest request){
        return userCustomization(model,request,"updateSuccesful");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public String register(){
        return "registration";
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/registerDish")
    public String registerDish(Model model, HttpServletRequest request, @PathVariable long id){
        model.addAttribute("id1",id);
        List<String> list= DishType.DESSERT.types();
        model.addAttribute("types", list);
        return userCustomization(model,request,"registrationDish");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/incorrectEmailOrPassword")
    public String incorrectEmailOPassword(){
        return "incorrectEmailOrPassword";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/alredyExistingUser")
    public String alredyExistingUser(){
        return "alreadyExistingUser";
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping ("/registerRestaurant")
    public String registerRestaurant( Model model, HttpServletRequest request){
        return userCustomization(model,request,"registrationRest");
    }

    //Giving the needed information to update a restaurant
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping ("/updateRestaurant/{id}")
    public String updateRestaurant(Model model, HttpServletRequest request, @PathVariable long id){
        Restaurant restaurant = restaurantManager.getRestaurant(id);

        model.addAttribute("id",id);
        model.addAttribute("restaurant",restaurant);

        return userCustomization(model,request,"updateRest");
    }

    //Giving the needed information to update a dish
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping ("/restaurant/{id1}/updateDish/{id2}")
    public String updateDishes(Model model, HttpServletRequest request, @PathVariable long id1 /*Restaurant ID*/, @PathVariable long id2/*Dish ID*/){

        Dishes dish = restaurantManager.getDish(id2);
        List<String> list=DishType.DESSERT.types();

        model.addAttribute("types", list);
        model.addAttribute("id1",id1);
        model.addAttribute("id2",id2);
        model.addAttribute("dish",dish);

        return userCustomization(model,request,"updateDish");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/faq")
    public String faq(Model model,HttpServletRequest request){
        return userCustomization(model,request,"faq");
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = {"/index","/"})
    public String index(Model model,HttpServletRequest request){
        return userCustomization(model,request,"index");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/forgottenPassword")
    public String forgottenPassword(){
        return "forgottenPassword";
    }

    private String userCustomization(Model model, HttpServletRequest request, String page){
        boolean logged=false;
        boolean admin=false;
        if(SecurityContextHolder.getContext().getAuthentication()!=null&&request.isUserInRole("ROLE_USER")){
            String username=request.getUserPrincipal().getName();
            logged=true;
            model.addAttribute("username",username);
        } else if(request.isUserInRole("ROLE_ADMIN")){
            admin=true;
        }
        model.addAttribute("logged",logged);
        model.addAttribute("admin",admin);
        return page;
    }
}
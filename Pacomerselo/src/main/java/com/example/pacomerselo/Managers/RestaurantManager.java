package com.example.pacomerselo.Managers;


import com.example.pacomerselo.Repositories.Dishes.DishesRepository;
import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Repositories.Restaurant.RestaurantRepository;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantManager{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DishesRepository dishesRepository;

    private final PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    //Adding a new restaurant and giving it its unique ID
    public void addRestaurant(Restaurant restaurant){
        restaurant.setDescription(policy.sanitize(restaurant.getDescription()));
        restaurantRepository.save(restaurant);
    }

    public Collection<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(String name){
        Optional<Restaurant> op= restaurantRepository.findByName(name);
        return op.orElse(null);
    }

    public void removeRestaurant (String name){
        dishesRepository.deleteAllDishes(restaurantRepository.getById(name));
        restaurantRepository.delete(restaurantRepository.getById(name));
    }

    public Restaurant updateRestaurant(String name,Restaurant newRestaurant){
        Optional<Restaurant> op= restaurantRepository.findById(name);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            restaurant.setName(newRestaurant.getName());
            restaurant.setDescription(policy.sanitize(newRestaurant.getDescription()));
            restaurant.setType(newRestaurant.getType());
            restaurantRepository.save(restaurant);
            return restaurant;
        }
        else{
            return null;
        }
    }

    //Adding a new dish and giving it its unique ID and its restaurant ID
    public Dishes addDish(String name, Dishes dish){
        Optional<Restaurant> op= restaurantRepository.findById(name);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            dish.setDescription(policy.sanitize(dish.getDescription()));
            restaurant.add(dish);
            dish.setRestaurant(restaurant);
            dishesRepository.save(dish);
            return dish;
        }else{
            return null;
        }
    }

    public Collection<Dishes> getDishes(String name){
        return restaurantRepository.getById(name).getDishesList();
    }

    public Dishes getDish(long id){
        Optional<Dishes> op= dishesRepository.findById(id);
        return op.orElse(null);
    }

    public int removeDish(long id){
        return dishesRepository.deleteDish(id);
    }

    public Dishes updateDish(long idDish,Dishes newDish){
        Optional<Dishes> op= dishesRepository.findById(idDish);
        if(op.isPresent()){
            Dishes dish=op.get();
            dish.setDescription(policy.sanitize(newDish.getDescription()));
            dish.setType(newDish.getType());
            dish.setPrice(newDish.getPrice());
            dishesRepository.save(dish);
            return dish;
        }
        else{
            return null;
        }
    }

    public List<Restaurant> findRestaurantByName(String name){
        return restaurantRepository.findRestaurantByName(name);
    }

    public List<Dishes> findDishByName (Restaurant restaurant,String name){
        return dishesRepository.findDishByName(restaurant,name);
    }

    public List<Dishes> findByPriceRangeAndType(int min, int max, String type,Restaurant restaurant){
        return dishesRepository.findByPriceRangeAndType(min, max, type,restaurant);
    }

    public List<Dishes> findByPriceRange(int min, int max, Restaurant restaurant){
        return dishesRepository.findByPriceRange(min,max,restaurant);
    }

    public List<Dishes> findByPriceRangeAndTwoTypes (int min, int max, String type1,String type2, Restaurant restaurant){
        return dishesRepository.findByPriceRangeAndTwoTypes(min, max, type1, type2, restaurant);
    }

}

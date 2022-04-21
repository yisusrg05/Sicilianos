package com.example.pacomerselo.Managers;


import com.example.pacomerselo.Repositories.Dishes.DishesRepository;
import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import com.example.pacomerselo.Repositories.Restaurant.RestaurantRepository;
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

    //Adding the default restaurants and dishes

    //Adding a new restaurant and giving it its unique ID
    public void addRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

    public Collection<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(long id){
        Optional<Restaurant> op= restaurantRepository.findById(id);
        return op.orElse(null);
    }

    public int removeRestaurant (long id){
        return restaurantRepository.deleteRestaurant(id);
    }

    public Restaurant updateRestaurant(long id,Restaurant newRestaurant){
        Optional<Restaurant> op= restaurantRepository.findById(id);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            restaurant.setName(newRestaurant.getName());
            restaurant.setDescription(newRestaurant.getDescription());
            restaurant.setType(newRestaurant.getType());
            restaurantRepository.save(restaurant);
            return restaurant;
        }
        else{
            return null;
        }
    }

    //Adding a new dish and giving it its unique ID and its restaurant ID
    public Dishes addDish(long idRestaurant, Dishes dish){
        Optional<Restaurant> op= restaurantRepository.findById(idRestaurant);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            restaurant.add(dish);
            dishesRepository.save(dish);
            restaurantRepository.save(restaurant);
            return dish;
        }else{
            return null;
        }
    }

    public Collection<Dishes> getDishes(long idRestaurant){
        return restaurantRepository.getById(idRestaurant).getDishesList();
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
            dish.setDescription(newDish.getDescription());
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
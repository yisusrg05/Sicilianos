package com.example.pacomerselo;


import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RestaurantHolder {

    private Map<Long,Restaurant> restaurants= new ConcurrentHashMap<>();
    private AtomicLong lastIDRestaurant= new AtomicLong();
    private AtomicLong lastIDdishes= new AtomicLong();

    public RestaurantHolder(){
        addRestaurant(new Restaurant("Sicilia","no sabe comer","Japonés"));
        addRestaurant(new Restaurant("Yisus","es un crack","Hawaiana"));
        addDish(1, new Dishes("Kevin Bacon", "La best seller",13));
        addDish(1, new Dishes("Pigma", "Mucho Huevo",12));
        addDish(1, new Dishes("Baby Yankee", "La mas Baby de todas",11));
        addDish(2, new Dishes("Big Mac", "Dos pisos",6));
    }


    public void addRestaurant(Restaurant restaurant){
        long id = lastIDRestaurant.incrementAndGet();
        restaurant.setId(id);
        restaurants.put(id,restaurant);
    }

    public Collection<Restaurant> getRestaurants(){
        return restaurants.values();
    }

    public Restaurant getRestaurant(long id){
        return restaurants.get(id);
    }

    public Restaurant removeRestaurant(long id){
        return restaurants.remove(id);
    }

    public void updateRestaurant(long id,Restaurant restaurant){
        restaurants.put(id,restaurant);
    }

    public void addDish(long idRestaurant, Dishes dish){
        long id = lastIDdishes.incrementAndGet();
        dish.setId(id);
        restaurants.get(idRestaurant).add(id,dish);
    }

    public Collection<Dishes> getDishes(long idRestaurant){
        return restaurants.get(idRestaurant).allDishes();
    }

    public Dishes getDish(long idRestaurant, long idDishes){
        return restaurants.get(idRestaurant).getDish(idDishes);
    }

    public Dishes removeDish(long idRestaurant, long idDishes){
        return restaurants.get(idRestaurant).removeDish(idDishes);
    }

    public void updateDish(long idRestaurant,long idDish,Dishes dish){
        restaurants.get(idRestaurant).add(idDish,dish);
    }
}

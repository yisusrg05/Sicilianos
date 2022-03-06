package com.example.pacomerselo;


import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RestaurantHolder {

    private Map<Long,Restaurant> restaurants= new ConcurrentHashMap<>();
    private AtomicLong lastID= new AtomicLong();

    public void addItem(Restaurant restaurant){
        long id = lastID.incrementAndGet();
        restaurant.setId(id);
        restaurants.put(id,restaurant);
    }

    public Collection<Restaurant> getRestaurants(){
        return restaurants.values();
    }

    public Restaurant getRestaurant(long id){
        return restaurants.get(id);
    }
}

package com.example.pacomerselo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
@Getter
@Setter
public class Restaurant {

    private String name;
    private String description;
    private long id=-1;
    private Map<Long,Dishes> dishes= new ConcurrentHashMap<>();

    public Restaurant(String name,String description){
        this.name=name;
        this.description=description;
    }

    public Dishes add(long id, Dishes dish){
        return this.dishes.put(id,dish);
    }

    public Collection<Dishes> allDishes (){
        return this.dishes.values();
    }

    public Dishes getDish (long id){
        return this.dishes.get(id);
    }

    public Dishes removeDish(long id){
        return this.dishes.remove(id);
    }
}

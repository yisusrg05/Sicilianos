package com.example.pacomerselo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Restaurant {
    //Attributes for the Restaurant Entity
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;
    private String type;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "restaurant")
    private List<Dishes> dishesList=new ArrayList<>();

    //Constructor for Restaurant:
    public Restaurant(String name,String description,String type){
        this.name=name;
        this.description=description;
        this.type=type;
    }
    //As we have a Map to collect dishes for the restaurant, we have the methods to add a dish to a Restaurant. It key is the id of the restaurant.
    public void add(Dishes dish){
        this.dishesList.add(dish);
    }
    /*
    //We have a method to get all the dishes from the Map which stores al the dishes of the restaurant.
    public Collection<Dishes> allDishes (){
        return this.dishes.values();
    }
    //Here we can get a dish with an id.
    public Dishes getDish (long id){
        return this.dishes.get(id);
    }
    //If we want to delete a Dish from a Restaurant, we need the Restaurant's Id, to get the value from the map, as it is the Key. We look for the Dish, and we delete it.
    public Dishes removeDish(long id){
        return this.dishes.remove(id);
    }*/
}

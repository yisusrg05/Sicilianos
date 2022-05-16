package com.example.pacomerselo.Entities;

import com.example.pacomerselo.Entities.Dishes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Restaurants")
public class Restaurant {
    //Attributes for the Restaurant Entity
    @Id
    private String name;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.DETACH,mappedBy = "restaurant")
    @JsonIgnore
    private List<Dishes> dishesList=new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    //Constructor for Restaurant:
    public Restaurant(String name,String description,String type, String email,String password, String... roles){
        this.name=name;
        this.description=description;
        this.type=type;
        this.email=email;
        this.password=password;
        this.roles=List.of(roles);
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

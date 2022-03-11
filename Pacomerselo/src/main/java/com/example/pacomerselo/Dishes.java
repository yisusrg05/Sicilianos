package com.example.pacomerselo;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class Dishes {
    //Attributes for Dishes:

    private String name;
    private String description;
    private int price;
    private long id;
    private long idRestaurant;

    //2 public constructors for Dishes: The first one receive name, description and price.
    //The second one the same as the first and also the id of the dish and the id of the Restaurant

    public Dishes(String name, String description, int price){
        this.name=name;
        this.description=description;
        this.price=price;
        this.id=-1;
        this.idRestaurant=-1;
    }

    public Dishes(String name, String description, int price, long id, long idRestaurant){
        this.name=name;
        this.description=description;
        this.price=price;
        this.id=id;
        this.idRestaurant=idRestaurant;
    }
}

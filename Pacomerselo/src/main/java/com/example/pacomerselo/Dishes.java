package com.example.pacomerselo;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlIDREF;

@Data
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Dishes {
    //Attributes for Dishes:
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;
    private int price;
    private String type;

    @ManyToOne
    private Restaurant restaurant;

    //2 public constructors for Dishes: The first one receive name, description and price.
    //The second one the same as the first and also the id of the dish and the id of the Restaurant

    public Dishes(String name, String description, int price, String type){
        this.name=name;
        this.description=description;
        this.price=price;
        this.type=type;
    }
}

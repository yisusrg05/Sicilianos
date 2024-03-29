package com.example.pacomerselo.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Dishes")
public class Dishes {
    //Attributes for Dishes:
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "dishes")
    @JsonIgnore
    private List<Order> orders=new ArrayList<>();

    //2 public constructors for Dishes: The first one receive name, description and price.
    //The second one the same as the first and also the id of the dish and the id of the Restaurant

    public Dishes(String name, String description, int price, String type){
        this.name=name;
        this.description=description;
        this.price=price;
        this.type=type;
    }

    public void add(Order order){
        this.orders.add(order);
    }
}

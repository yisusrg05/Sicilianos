package com.example.pacomerselo.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



@Getter
@Setter
@Entity
@Table(name="Orders")
public class Order {
    //As web has @NoArgsConstructor, we do not need to create a new Constructor. Moreover, the Order's Attributes are the price, a list and an id.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Transient
    private static long lastId=0;

    private long orderNumber=0;
    private int price;
    private boolean discount;
    private int discountedPrice;

    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToMany
    @JoinTable(
            name="dishes_ordered",
            joinColumns= @JoinColumn(name = "order_id"),
            inverseJoinColumns= @JoinColumn(name="dish_id")
    )
    @JsonManagedReference
    private List<Dishes> dishes=new ArrayList<>();

    public Order(){
        this.orderNumber=lastId++;
        this.price=5;
    }

    public Order(int price){
        this.price=price+5;
    }

    public void add(Dishes dish){
        this.dishes.add(dish);
        this.price+=dish.getPrice();
    }
}

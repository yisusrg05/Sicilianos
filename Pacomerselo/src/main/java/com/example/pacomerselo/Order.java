package com.example.pacomerselo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@Setter
@Entity
@Table(name="Orders")
public class Order {
    //As web has @NoArgsConstructor, we do not need to create a new Constructor. Moreover, the Order's Attributes are the price, a list and an id.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int price;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name="dishes_ordered",
            joinColumns= @JoinColumn(name = "order_id"),
            inverseJoinColumns= @JoinColumn(name="dish_id")
    )
    private List<Dishes> dishes=new ArrayList<>();

    public Order(){
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

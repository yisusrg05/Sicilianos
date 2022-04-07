package com.example.pacomerselo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Order {
    //As web has @NoArgsConstructor, we do not need to create a new Constructor. Moreover, the Order's Attributes are the price, a list and an id.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int price=0;

    @ManyToMany
    private List<Dishes> dishes;

    @ManyToOne
    private User user;


    public Order(int price){
        this.price=price;
    }
}

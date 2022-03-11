package com.example.pacomerselo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Data
@NoArgsConstructor
@Getter
@Setter
public class Order {
    //As web have @NoArgsConstructor, we do not need to create a new Constructor. Moreover, the Order's Attributes, are the price, a list and an id.

    private int price=0;
    private List<Dishes> dishes;
    private long id=-1;

    public Order(List<Dishes> dish){
        this.dishes=dish;
    }
}

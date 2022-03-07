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

    private int price=0;
    private List<Dishes> dishes;
    private long id=-1;

    public Order(List<Dishes> dish){
        this.dishes=dish;
    }
}

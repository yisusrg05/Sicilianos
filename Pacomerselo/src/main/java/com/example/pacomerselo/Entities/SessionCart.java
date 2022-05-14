package com.example.pacomerselo.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@SessionScope
public class SessionCart {

    private final static long SHIPPING_COSTS=5;

    private List<Dishes> dishesList;
    private long price;
    private long total;

    public SessionCart(){
        this.dishesList= new ArrayList<>();
        this.price=0;
        this.total=SHIPPING_COSTS;
    }

    public List<Dishes> getCart() {
        if (dishesList == null) {
            dishesList = new ArrayList<>();
        }
        return dishesList;
    }

    public void setCart(List<Dishes> cart) {
        this.dishesList = cart;
    }

    private void calculatePrice(){
        for(Dishes dish : dishesList){
            this.price+=dish.getPrice();
            this.total+=dish.getPrice();
        }
    }

    public void add(Dishes dish){
        this.dishesList.add(dish);
        calculatePrice();
    }

    public void deleteDish(long id){
        dishesList.removeIf(dish -> dish.getId() == id);
    }

}

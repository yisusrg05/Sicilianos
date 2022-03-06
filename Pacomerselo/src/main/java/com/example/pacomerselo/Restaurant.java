package com.example.pacomerselo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
@Getter
@Setter
public class Restaurant {

    private String name;
    private String description;
    private List<Dishes> dishesList;
    private long id=-1;
    private AtomicLong lastID= new AtomicLong();

    public Restaurant(String name,String description){
        this.name=name;
        this.description=description;
        this.dishesList= new ArrayList<>();
    }

    public void add(Dishes dish){
        long idDish = lastID.incrementAndGet();
        dish.setId(idDish);
        this.dishesList.add(dish);
    }
}

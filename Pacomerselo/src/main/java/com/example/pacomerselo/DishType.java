package com.example.pacomerselo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DishType {
    STARTER("Entrante"),
    MAIN("Principal"),
    DESSERT("Postre");


    public String label;

}

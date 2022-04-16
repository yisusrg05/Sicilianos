package com.example.pacomerselo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DishType {
    STARTER(0,"Entrante"),
    PRINCIPAL(1,"Principal"),
    DESSERT(2,"Postre");

    int code;
    String label;

    //★ Point 1
    public String getName() {
        return name();
    }
}

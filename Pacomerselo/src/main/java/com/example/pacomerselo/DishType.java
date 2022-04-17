package com.example.pacomerselo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DishType {
    STARTER("Entrante"),
    MAIN("Principal"),
    DESSERT("Postre");

    public String toString(){
        switch (this){
            case STARTER : return "Entrante";
            case MAIN: return "Principal";
            default: return "Postre";
        }
    }


    public String label;

}

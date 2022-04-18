package com.example.pacomerselo.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> types(){
        List<String> types= new ArrayList<>();
        for (DishType dt : DishType.values()){
            types.add(dt.label);
        }
        return types;
    }

}

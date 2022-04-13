package com.example.pacomerselo;

public enum DishType {
    STARTER, PRINCIPAL, DESSERT;

    public String toString(){
        switch (this){
            case STARTER: return "Entrante";
            case DESSERT: return "Postre";
            default: return "Principal";
        }
    }
}

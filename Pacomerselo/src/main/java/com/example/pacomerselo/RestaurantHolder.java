package com.example.pacomerselo;


import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RestaurantHolder {

    private Map<Long,Restaurant> restaurants= new ConcurrentHashMap<>();
    private AtomicLong lastIDRestaurant= new AtomicLong();
    private AtomicLong lastIDdishes= new AtomicLong();

    //Adding the default restaurants and dishes
    public RestaurantHolder(){
        addRestaurant(new Restaurant("La Bonita","Comida Original","Mediterránea"));
        addRestaurant(new Restaurant("Fellina","Comida Italiana Sofisticada","Italiana"));
        addDish(1, new Dishes("Croquetas Gourmet", "6ud",12,"Entrante"));
        addDish(1, new Dishes("Ensalada de Burrata", "Con miel y trufa",20,"Entrante"));
        addDish(1, new Dishes("Solomillo de Ternera", "Con patatas baby especiadas",25,"Principal"));
        addDish(1, new Dishes("Cappellacci", "Relleno de verduras",15,"Principal"));
        addDish(2, new Dishes("Ensalada de Queso de Cabra", "Con tomate y rúcula",17,"Entrante"));
        addDish(2, new Dishes("Pappardelle", "Con ternera y queso",19,"Principal"));
        addDish(2, new Dishes("Tabla embutidos", "Jamón, focaccia, ...",9,"Entrante"));
    }

    //Adding a new restaurant and giving it its unique ID
    public void addRestaurant(Restaurant restaurant){
        long id = lastIDRestaurant.incrementAndGet();
        restaurant.setId(id);
        restaurants.put(id,restaurant);
    }

    public Collection<Restaurant> getRestaurants(){
        return restaurants.values();
    }

    public Restaurant getRestaurant(long id){
        return restaurants.get(id);
    }

    public Restaurant removeRestaurant(long id){
        return restaurants.remove(id);
    }

    public void updateRestaurant(long id,Restaurant restaurant){
        restaurants.put(id,restaurant);
    }

    //Adding a new dish and giving it its unique ID and its restaurant ID
    public void addDish(long idRestaurant, Dishes dish){
        long id = lastIDdishes.incrementAndGet();
        dish.setId(id);
        dish.setIdRestaurant(idRestaurant);
        restaurants.get(idRestaurant).add(id,dish);
    }

    public Collection<Dishes> getDishes(long idRestaurant){
        return restaurants.get(idRestaurant).allDishes();
    }

    public Dishes getDish(long idRestaurant, long idDishes){
        return restaurants.get(idRestaurant).getDish(idDishes);
    }

    public Dishes removeDish(long idRestaurant, long idDishes){return restaurants.get(idRestaurant).removeDish(idDishes);}

    public void updateDish(long idRestaurant,long idDish,Dishes dish){
        restaurants.get(idRestaurant).add(idDish,dish);
    }
}

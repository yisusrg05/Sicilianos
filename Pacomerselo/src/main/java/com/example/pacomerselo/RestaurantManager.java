package com.example.pacomerselo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RestaurantManager {


    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DishesRepository dishesRepository;

    //Adding the default restaurants and dishes
    public RestaurantManager(){
        Restaurant laBonita=new Restaurant("La Bonita","Comida Original","Mediterránea");
        Restaurant fellina=new Restaurant("Fellina","Comida Italiana Sofisticada","Italiana");

        restaurantRepository.save(laBonita);

        restaurantRepository.save(fellina);



        Dishes dish11=new Dishes("Croquetas Gourmet", "6ud",12,"Entrante");
        Dishes dish12=new Dishes("Ensalada de Burrata", "Con miel y trufa",20,"Entrante");
        Dishes dish13=new Dishes("Solomillo de Ternera", "Con patatas baby especiadas",25,"Principal");
        Dishes dish14=new Dishes("Cappellacci", "Relleno de verduras",15,"Principal");
        Dishes dish21=new Dishes("Ensalada de Queso de Cabra", "Con tomate y rúcula",17,"Entrante");
        Dishes dish22=new Dishes("Pappardelle", "Con ternera y queso",19,"Principal");
        Dishes dish23= new Dishes("Tabla embutidos", "Jamón, focaccia, ...",9,"Entrante");

        laBonita.add(dish11);
        laBonita.add(dish12);
        laBonita.add(dish13);
        laBonita.add(dish14);

        fellina.add(dish21);
        fellina.add(dish22);
        fellina.add(dish23);

        dishesRepository.save(dish11);
        dishesRepository.save(dish12);
        dishesRepository.save(dish13);
        dishesRepository.save(dish14);
        dishesRepository.save(dish21);
        dishesRepository.save(dish22);
        dishesRepository.save(dish23);

    }

    //Adding a new restaurant and giving it its unique ID
    public void addRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

    public Collection<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(long id){
        Optional<Restaurant> op= restaurantRepository.findById(id);
        return op.orElse(null);
    }

    public Restaurant removeRestaurant(long id){
        Optional<Restaurant> op= restaurantRepository.findById(id);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            restaurant.getDishesList().size();
            restaurantRepository.deleteById(id);
            return restaurant;
        }else{
            return null;
        }
    }

    public Restaurant updateRestaurant(long id,Restaurant newRestaurant){
        Optional<Restaurant> op= restaurantRepository.findById(id);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            restaurant.setName(newRestaurant.getName());
            restaurant.setDescription(newRestaurant.getDescription());
            restaurant.setType(newRestaurant.getType());
            restaurantRepository.save(restaurant);
            return restaurant;
        }
        else{
            return null;
        }
    }

    //Adding a new dish and giving it its unique ID and its restaurant ID
    public Restaurant addDish(long idRestaurant, Dishes dish){
        Optional<Restaurant> op= restaurantRepository.findById(idRestaurant);
        if(op.isPresent()){
            Restaurant restaurant=op.get();
            dishesRepository.save(dish);
            restaurant.add(dish);
            restaurantRepository.save(restaurant);
            return restaurant;
        }else{
            return null;
        }
    }

    public Collection<Dishes> getDishes(long idRestaurant){
        return restaurantRepository.getById(idRestaurant).getDishesList();
    }

    public Dishes getDish(long id){
        Optional<Dishes> op= dishesRepository.findById(id);
        return op.orElse(null);
    }

    public Dishes removeDish(long id){
        Optional<Dishes> op= dishesRepository.findById(id);
        if(op.isPresent()){
            Dishes dish=op.get();
            dishesRepository.deleteById(id);
            return dish;
        }
        else{
            return null;
        }
    }

    public Dishes updateDish(long idDish,Dishes newDish){
        Optional<Dishes> op= dishesRepository.findById(idDish);
        if(op.isPresent()){
            Dishes dish=op.get();
            dish.setName(newDish.getName());
            dish.setDescription(newDish.getDescription());
            dish.setType(newDish.getType());
            dish.setPrice(newDish.getPrice());
            dishesRepository.save(dish);
            return dish;
        }
        else{
            return null;
        }
    }
}

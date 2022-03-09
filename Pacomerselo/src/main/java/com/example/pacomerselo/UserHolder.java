package com.example.pacomerselo;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserHolder {

    private Map<Long,User> users= new ConcurrentHashMap<>();
    private AtomicLong lastIDUser= new AtomicLong();
    private AtomicLong lastIDOrder= new AtomicLong();
    private Map<String,Long> userIDs= new ConcurrentHashMap<>();

    public UserHolder(){
        addUser(new User("Anonimo","Sicilia","Crack","sici@urjc.es","oleole"));
        addDishToCart(1,new Dishes("Kevin","Bacon",14));
        addDishToCart(1,new Dishes("Pigma","Huevo",11));
        addDishToCart(1,new Dishes("Pigma","Huevo",11));
    }

    public void addUser(User user){
        long id = lastIDUser.incrementAndGet();
        user.setId(id);
        users.put(id,user);
        userIDs.put(user.getUsername(),id);
    }

    public User getUser(long id){
        return users.get(id);
    }

    public User removeUser(long id){
        return users.remove(id);
    }

    public void updateUser(long id,User user){
        users.put(id,user);
        userIDs.put(user.getUsername(),id);
    }

    public boolean validUser(String username,String password){
        long id;
        if(userIDs.containsKey(username)){
            id = userIDs.get(username);
        }else{
            id=0;
        }
        if(id==0){
            return false;
        }else{
            return users.get(id).getUsername().equals(username)&&users.get(id).getPassword().equals(password);
        }
    }

    public void addDishToCart(long id,Dishes dish){
        users.get(id).addDish(dish);
    }

    public void deleteDishFromCart(long id, Dishes dish){
        users.get(id).deleteDish(dish);
    }

    public List<Dishes> getDishes(long id){
        return users.get(id).allCart();
    }

    public void proccessOrder (long id){
        Order order = new Order((List<Dishes>) getDishes(id));
        int price=0;
        List<Dishes> list =(List<Dishes>) getDishes(id);
        for(Dishes dish : list){
            price+=dish.getPrice();
        }
        long idOrder = lastIDOrder.incrementAndGet();
        order.setId(idOrder);
        users.get(id).addOrder(idOrder,order);
    }

    // YA SE VERÁ MÁS ADELANTE QUE COJONES HACEMOS CON ESTO

    /*public Dishes removeDish(long idRestaurant, long idDishes){
        return restaurants.get(idRestaurant).removeDish(idDishes);
    }*/

    /*public void updateDish(long idRestaurant,long idDish,Dishes dish){
        restaurants.get(idRestaurant).add(idDish,dish);
    }*/
}

package com.example.pacomerselo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    static final int SHIPPING_COSTS = 5;

    public UserHolder(){ //Adding our single user (for now)
        addUser(new User("Anonimo","Paco","Mérselo","paco@pacomerselo.es","oleole"));
    }

    //Adding a user, plus setting its ID, adding it to the user map and adding it to our <Username,ID> map
    public void addUser(User user){
        long id = lastIDUser.incrementAndGet();
        user.setId(id);
        users.put(id,user);
        userIDs.put(user.getUsername(),id);
    }

    public User getUser(long id){
        return users.get(id);
    }

    public User getUser(String user){
        return users.get(userIDs.get(user));
    }

    public String getPassword(long id){
        return users.get(id).getPassword();
    }

    public User removeUser(long id){
        userIDs.remove(users.get(id).getUsername()); //Remove from the <Username,ID> Map
        return users.remove(id);
    }

    //Updating an existing user
    public void updateUser(long id,User user){
        user.setOrders(users.get(id).getOrders());//Set the new User orders to the old one
        user.setCart(users.get(id).getCart());//Set the new User cart to the old one
        users.put(id,user);//Overwrite the old user
        userIDs.put(user.getUsername(),id); //Overwrite the old registe
    }

    //The login function
    public boolean validUser(String username,String password){
        long id;
        if(userIDs.containsKey(username)){ //Checking if the username is from a valid user
            id = userIDs.get(username);
        }else{
            id=0;
        }
        if(id==0){ //If it does not exist, faile login
            return false;
        }else{ //If it exits, we check that the information given is the same as the one stored
            return users.get(id).getUsername().equals(username)&&users.get(id).getPassword().equals(password);
        }
    }

    public void addDishToCart(long id,Dishes dish){
        users.get(id).addDish(dish);
    }

    public void deleteDishFromCart(long id, Dishes dish){
        users.get(id).deleteDish(dish);
    }

    public void deleteCart(long id){
        users.get(id).setCart(new ArrayList<Dishes>());
    }

    public List<Dishes> getDishes(long id){
        return users.get(id).allCart();
    }


    //Delete the cart and create a new order, given an user ID
    public long proccessOrder (long id){
        Order order = new Order((List<Dishes>) getDishes(id));
        int price=SHIPPING_COSTS;
        List<Dishes> list =(List<Dishes>) getDishes(id);
        for(Dishes dish : list){
            price+=dish.getPrice();
        }
        long idOrder = lastIDOrder.incrementAndGet();
        order.setId(idOrder);
        order.setPrice(price);
        deleteCart(id);
        users.get(id).addOrder(idOrder,order);
        return idOrder;
    }

    //Given a user ID and a dish ID, check if that dish is in the cart, and if so, return it
    public Dishes getDishFromCart(long idUser,long id){
        List<Dishes> dishesList=users.get(idUser).getCart();
        Dishes validDish=null;
        for (Dishes dish: dishesList){
            if (dish.getId()==id){
                validDish=dish;
            }
        }
        return validDish;
    }

    public Map<Long,Order> getOrders (long id){
        return users.get(id).getOrders();
    }

}

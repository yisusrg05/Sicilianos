package com.example.pacomerselo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;



@Service
public class UserManager {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    static final int SHIPPING_COSTS = 5;

    @PostConstruct
    public void init(){ //Adding our single user (for now)
        User admin=new User("Anonimo","Paco","Mérselo","paco@pacomerselo.es","oleole");
        userRepository.save(admin);

    }

    //Adding a user, plus setting its ID, adding it to the user map and adding it to our <Username,ID> map
    public void addUser(User user){
        userRepository.save(user);
    }

    public User getUser(long id){
        Optional<User> op= userRepository.findById(id);
        return op.orElse(null);

    }


    public String getPassword(long id){
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User user=op.get();
            return user.getPassword();
        }
        else{
            return null;
        }
    }

    public User removeUser(long id){
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User user=op.get();
            user.getOrders().size();
            userRepository.deleteById(id);
            return user;
        }else{
            return null;
        }
    }

    //Updating an existing user
    public User updateUser(long id,User newUser){
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User user=op.get();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            user.setSurname(newUser.getSurname());
            userRepository.save(user);
            return user;
        }
        else{
            return null;
        }
    }
    /*

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
    */

    public Collection<Order> getOrders (long id){
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User user=op.get();
            return user.getOrders();
        }
        else{
            return null;
        }
    }

}

package com.example.pacomerselo.Managers;

import com.example.pacomerselo.Entities.Order;
import com.example.pacomerselo.Entities.User;
import com.example.pacomerselo.Repositories.Order.OrderRepository;
import com.example.pacomerselo.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserManager extends RestaurantManager {

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;


    static final int SHIPPING_COSTS = 5;

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
            return user.getEncodedPassword();
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
    public int updateUser(long id,User newUser){
        return userRepository.updateUser(id, newUser.getName(), newUser.getSurname(), newUser.getEmail());
    }

    public User updateUserPassword(User user,String password){
        long id= user.getId();
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User validUser=op.get();
            validUser.setEncodedPassword(password);
            userRepository.save(validUser);
            return validUser;
        }
        else{
            return null;
        }
    }

    public List<User> findByUsernameAndEmail(String username, String email){
        return userRepository.findByUsernameAndEmail(username, email);
    }

    public List<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<Order> findOrdersByUsername(String username){
        return orderRepository.findByUsername(username);
    }

    public List<Order> findOrdersByUser(User user){
        return orderRepository.findOrdersByUser(user);
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

}

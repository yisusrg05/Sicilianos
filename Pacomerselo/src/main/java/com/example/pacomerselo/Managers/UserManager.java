package com.example.pacomerselo.Managers;

import com.example.pacomerselo.Entities.*;
import com.example.pacomerselo.Repositories.Order.OrderRepository;
import com.example.pacomerselo.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserManager{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    static final int SHIPPING_COSTS = 5;

    //Adding a user, plus setting its ID, adding it to the user map and adding it to our <Username,ID> map
    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(String username){
        Optional<User> op= userRepository.findByUsername(username);
        return op.orElse(null);
    }


    public String getPassword(String username){
        Optional<User> op= userRepository.findByUsername(username);
        if(op.isPresent()){
            User user=op.get();
            return user.getPassword();
        }
        else{
            return null;
        }
    }

    public User removeUser(String username){
        Optional<User> op= userRepository.findByUsername(username);
        if(op.isPresent()){
            User user=op.get();
            user.getOrders().size();
            userRepository.delete(user);
            return user;
        }else{
            return null;
        }
    }

    //Updating an existing user
    public int updateUser(String username,User newUser){
        return userRepository.updateUser(username, newUser.getName(), newUser.getSurname(), newUser.getEmail());
    }

    public User updateUserPassword(User user,String password){
        Optional<User> op= userRepository.findByUsername(user.getUsername());
        if(op.isPresent()){
            User validUser=op.get();
            validUser.setPassword(password);
            userRepository.save(validUser);
            return validUser;
        }
        else{
            return null;
        }
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsernameAndEmail(String username, String email){
        return userRepository.findByUsernameAndEmail(username, email);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public List<Order> findOrdersByUsername(String username){
        return orderRepository.findByUsername(username);
    }

    public List<Order> findOrdersByUser(User user){
        return orderRepository.findOrdersByUser(user);
    }



    //Delete the cart and create a new order, given an user ID
    public void proccessOrder(String username, SessionCart cart){
        Order order= new Order();
        for(Dishes dish : cart.getDishesList()) {
            order.add(dish);
        }
        if(order.getPrice()==(int) cart.getTotal()){
            order.setDiscount(false);
            order.setDiscountedPrice(order.getPrice());
        }
        else{
            order.setDiscount(true);
            order.setDiscountedPrice((int) cart.getTotal());
        }
        order.setUser(getUser(username));
        orderRepository.save(order);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public void addOAuthUser(String name, String email, AuthenticationProvider authProvider,OAuth2AuthenticationToken token){
        User userAdded=new User(name, email,authProvider);
        List<String> roles= new ArrayList<>();
        roles.add("USER");
        userAdded.setRoles(roles);
        userAdded.setPassword(passwordEncoder.encode(token.toString()));
        userRepository.save(userAdded);
    }

    public void updateOAuthUser(String name, String email, AuthenticationProvider authProvider){
        userRepository.findByEmail(email).orElse(null);
    }


   /* public void addNewUserAfterOAuthLoginSucces(String email, String name, AuthenticationProvider provider) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setAuthProvider(provider);

        userRepository.save(user);
    }

    public void updateUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider provider) {
        user.setName(name);
        user.setAuthProvider(provider);
        userRepository.save(user);
    }
    */

}

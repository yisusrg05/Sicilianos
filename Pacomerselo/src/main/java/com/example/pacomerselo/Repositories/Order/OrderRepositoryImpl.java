package com.example.pacomerselo.Repositories.Order;

import com.example.pacomerselo.Entities.Order;
import com.example.pacomerselo.Entities.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class OrderRepositoryImpl{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findOrdersByUser(User user) {
        TypedQuery<Order> query= entityManager.createQuery
                ("SELECT o FROM Order o WHERE o.user=:user",Order.class);
        return query.setParameter("user",user).getResultList();
    }

    public List<Order> findByUsername(String username){
        TypedQuery<Order> query= entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.user IN (SELECT u FROM User u WHERE u.username=:username)" ,Order.class);
        return query.setParameter("username",username).getResultList();
    }
}

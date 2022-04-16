package com.example.pacomerselo;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class OrderRepositoryImpl{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findbyNameRestaurant(User user) {
        TypedQuery<Order> query= entityManager.createQuery
                ("SELECT o FROM Order o WHERE o.user=:user",Order.class);
        return query.setParameter("user",user).getResultList();
    }
}

package com.example.pacomerselo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Component
public class RestaurantRepositoryImpl{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Restaurant> findbyNameRestaurant(String nombre) {
        TypedQuery<Restaurant> query= entityManager.createQuery
                ("SELECT r FROM Restaurant r WHERE (r.name LIKE CONCAT('%',:nombre,'%'))",Restaurant.class);
        return query.setParameter("nombre",nombre).getResultList();
    }

    public List<Dishes> findbyNameDish(Restaurant restaurant,String nombre) {
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.restaurant=:restaurant AND (d.name LIKE CONCAT('%',:nombre,'%'))",Dishes.class);
        return query.setParameter("nombre",nombre).setParameter("restaurant",restaurant).getResultList();
    }

    public List<Dishes> findbyPriceRangeDish(Long id,int top, int bottom) {
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.restaurant=:idRest AND d.price BETWEEN :bottom AND :top",Dishes.class);
        return query.setParameter("top",top).setParameter("bottom",bottom).setParameter("idRest",id).getResultList();
    }
}

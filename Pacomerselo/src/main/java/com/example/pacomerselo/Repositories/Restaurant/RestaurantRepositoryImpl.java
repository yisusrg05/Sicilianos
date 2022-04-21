package com.example.pacomerselo.Repositories.Restaurant;

import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Component
public class RestaurantRepositoryImpl{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Restaurant> findRestaurantByName(String name) {
        TypedQuery<Restaurant> query= entityManager.createQuery
                ("SELECT r FROM Restaurant r WHERE (r.name LIKE CONCAT('%',:name,'%'))",Restaurant.class);
        return query.setParameter("name",name).getResultList();
    }

    @Transactional
    public int deleteRestaurant(long id){
        Query query = entityManager.createQuery
                ("DELETE FROM Restaurant r WHERE r.id=:id");
        return query.setParameter("id",id).executeUpdate();
    }
}

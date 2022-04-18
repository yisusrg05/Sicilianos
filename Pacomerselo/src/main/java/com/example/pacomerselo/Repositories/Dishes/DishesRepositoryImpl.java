package com.example.pacomerselo.Repositories.Dishes;

import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class DishesRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Dishes> findByPriceRangeAndType (int min, int max, String type,Restaurant restaurant){
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.price BETWEEN :min AND :max AND d.type=:type AND d.restaurant=:restaurant",Dishes.class);
        return query.setParameter("min",min).setParameter("max",max).setParameter("type",type).setParameter("restaurant",restaurant).getResultList();
    }

}

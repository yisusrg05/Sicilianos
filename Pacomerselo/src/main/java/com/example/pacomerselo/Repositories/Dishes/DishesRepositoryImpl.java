package com.example.pacomerselo.Repositories.Dishes;

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
public class DishesRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Dishes> findDishByName(Restaurant restaurant, String name) {
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.restaurant=:restaurant AND (d.name LIKE CONCAT('%',:name,'%'))",Dishes.class);
        return query.setParameter("name",name).setParameter("restaurant",restaurant).getResultList();
    }

    public List<Dishes> findByPriceRangeAndType (int min, int max, String type,Restaurant restaurant){
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.price BETWEEN :min AND :max AND d.type=:type AND d.restaurant=:restaurant",Dishes.class);
        return query.setParameter("min",min).setParameter("max",max).setParameter("type",type).setParameter("restaurant",restaurant).getResultList();
    }

    public List<Dishes> findByPriceRangeAndTwoTypes (int min, int max, String type1,String type2, Restaurant restaurant){
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.price BETWEEN :min AND :max AND (d.type=:type1 OR d.type=:type2) AND d.restaurant=:restaurant",Dishes.class);
        return query.setParameter("min",min).setParameter("max",max).setParameter("type1",type1).setParameter("type2",type2).setParameter("restaurant",restaurant).getResultList();
    }


    public List<Dishes> findByPriceRange(int min, int max, Restaurant restaurant){
        TypedQuery<Dishes> query= entityManager.createQuery
                ("SELECT d FROM Dishes d WHERE d.price BETWEEN :min AND :max AND d.restaurant=:restaurant",Dishes.class);
        return query.setParameter("min",min).setParameter("max",max).setParameter("restaurant",restaurant).getResultList();
    }

    @Transactional
    public int deleteDish(long id){
        Query query = entityManager.createQuery
                ("UPDATE Dishes d SET d.restaurant=null WHERE d.id=:id");
        return query.setParameter("id",id).executeUpdate();
    }

}

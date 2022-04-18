package com.example.pacomerselo.Repositories.Dishes;

import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes,Long> {

    public List<Dishes> findByPriceRangeAndType (int min, int max, String type, Restaurant restaurant);
}

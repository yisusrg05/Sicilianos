package com.example.pacomerselo.Repositories.Dishes;

import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes,Long> {

    public List<Dishes> findDishByName(Restaurant restaurant, String name);

    public List<Dishes> findByPriceRangeAndType (int min, int max, String type, Restaurant restaurant);

    public List<Dishes> findByPriceRangeAndTwoTypes (int min, int max, String type1,String type2, Restaurant restaurant);


    public List<Dishes> findByPriceRange(int min, int max, Restaurant restaurant);

    @Transactional
    public int deleteDish(long id);
}

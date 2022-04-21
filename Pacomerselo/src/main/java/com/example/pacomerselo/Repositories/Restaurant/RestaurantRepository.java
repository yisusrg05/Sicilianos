package com.example.pacomerselo.Repositories.Restaurant;

import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    public List<Restaurant> findRestaurantByName(String name);

    @Transactional
    public int deleteRestaurant(long id);
}

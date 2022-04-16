package com.example.pacomerselo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    public List<Restaurant> findbyNameRestaurant(String nombre);

    public List<Dishes> findbyNameDish(Restaurant restaurant,String nombre);

    public List<Dishes> findbyPriceRangeDish(Long id, int top, int bottom);
}

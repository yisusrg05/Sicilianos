package com.example.pacomerselo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    public List<Restaurant> findbyNameRestaurant(String nombre);

    public List<Dishes> findbyNameDish(Long id,String nombre);

    public List<Dishes> findbyPriceRangeDish(Long id, int top, int bottom);
}

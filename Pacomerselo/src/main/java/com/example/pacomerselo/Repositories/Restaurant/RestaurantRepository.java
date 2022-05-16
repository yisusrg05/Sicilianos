package com.example.pacomerselo.Repositories.Restaurant;

import com.example.pacomerselo.Entities.Dishes;
import com.example.pacomerselo.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String>{

    List<Restaurant> findRestaurantByName(String name);

    Optional<Restaurant> findByName(String name);
}

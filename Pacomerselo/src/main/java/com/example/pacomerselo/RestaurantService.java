package com.example.pacomerselo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantService extends JpaRepository<Restaurant,Long> {
}

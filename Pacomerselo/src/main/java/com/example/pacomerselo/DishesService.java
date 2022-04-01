package com.example.pacomerselo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DishesService extends JpaRepository<Dishes,Long> {
}

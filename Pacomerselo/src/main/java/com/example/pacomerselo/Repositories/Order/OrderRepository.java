package com.example.pacomerselo.Repositories.Order;

import com.example.pacomerselo.Entities.Order;
import com.example.pacomerselo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    public List<Order> findbyNameRestaurant(User user);
}

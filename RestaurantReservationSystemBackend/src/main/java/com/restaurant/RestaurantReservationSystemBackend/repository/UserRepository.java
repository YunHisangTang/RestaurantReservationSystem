package com.restaurant.RestaurantReservationSystemBackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.RestaurantReservationSystemBackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}


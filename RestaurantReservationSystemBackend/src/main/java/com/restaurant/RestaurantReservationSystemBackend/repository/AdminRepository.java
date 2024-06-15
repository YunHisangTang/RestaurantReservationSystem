package com.restaurant.RestaurantReservationSystemBackend.repository;

import com.restaurant.RestaurantReservationSystemBackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}

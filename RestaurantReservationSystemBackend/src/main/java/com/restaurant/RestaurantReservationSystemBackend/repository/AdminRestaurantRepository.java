package com.restaurant.RestaurantReservationSystemBackend.repository;

import com.restaurant.RestaurantReservationSystemBackend.model.AdminRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRestaurantRepository extends JpaRepository<AdminRestaurant, Long> {
    List<AdminRestaurant> findByAdminId(Long adminId);
    void deleteByRestaurantId(Long restaurantId);
}

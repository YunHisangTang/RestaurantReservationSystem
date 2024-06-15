package com.restaurant.RestaurantReservationSystemBackend.repository;

import com.restaurant.RestaurantReservationSystemBackend.model.ReservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {
    Optional<ReservationSlot> findByRestaurantId(Long restaurantId);
    void deleteByRestaurantId(Long restaurantId);
}

package com.restaurant.RestaurantReservationSystemBackend.repository;

import com.restaurant.RestaurantReservationSystemBackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserIdAndStatusNotOrderByReservationDateTimeAsc(Long userId, String status);
    List<Reservation> findByRestaurantIdAndReservationDateTimeBetween(Long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Reservation> findByRestaurantIdOrderByIdDesc(Long restaurantId);
}

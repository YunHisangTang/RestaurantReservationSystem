package com.restaurant.RestaurantReservationSystemBackend.model;


import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime reservationDateTime;
    private int numAdults;
    private int numChildren;
    private boolean isSameTable;
    private String status; // Completed, Booked, Deleted
    private LocalDateTime completionTime;
    private LocalDateTime diningStartTime;
    private LocalDateTime diningEndTime;

}


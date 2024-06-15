package com.restaurant.RestaurantReservationSystemBackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime reservationDateTime;
    private int numAdults;
    private int numChildren;
    private boolean isSameTable;
    private String status; // Completed, Booked, Deleted
    private String userPhoneNumber;
    private String restaurantName;
}

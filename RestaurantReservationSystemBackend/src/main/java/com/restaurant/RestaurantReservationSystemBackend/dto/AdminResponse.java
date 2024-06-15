package com.restaurant.RestaurantReservationSystemBackend.dto;

import lombok.Data;

@Data
public class AdminResponse {
    private final String token;
    private final Long id;
    private final String phoneNumber;
    private final String name;
    private final String email;
    private final int points;
}

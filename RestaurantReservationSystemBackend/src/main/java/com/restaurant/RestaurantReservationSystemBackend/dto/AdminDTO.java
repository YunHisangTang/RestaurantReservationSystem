package com.restaurant.RestaurantReservationSystemBackend.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private Long id;
    private String phoneNumber;
    private String name;
    private String email;
    private String password;
    private int points;
}

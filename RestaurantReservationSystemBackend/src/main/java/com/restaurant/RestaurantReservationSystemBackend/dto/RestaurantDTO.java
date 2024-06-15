package com.restaurant.RestaurantReservationSystemBackend.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantDTO {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String description;
    private String menu;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int diningDuration;
    private int capacity;
}

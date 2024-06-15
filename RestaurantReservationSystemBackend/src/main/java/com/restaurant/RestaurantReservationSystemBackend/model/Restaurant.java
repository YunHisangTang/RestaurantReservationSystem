package com.restaurant.RestaurantReservationSystemBackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.time.LocalTime;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String description;
    private String menu;
    private LocalTime openingTime;  // 開店時間
    private LocalTime closingTime;  // 關店時間
    private int diningDuration;     // 用餐間隔時間，分鐘
    private int capacity;           // 最大容纳人数


}

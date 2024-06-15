package com.restaurant.RestaurantReservationSystemBackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReservationSlotDTO {
    private Long restaurantId;
    private List<String> times;

    public ReservationSlotDTO(Long restaurantId, List<String> times){
        this.restaurantId = restaurantId;
        this.times = times;
    }
}

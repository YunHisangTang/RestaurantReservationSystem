package com.restaurant.RestaurantReservationSystemBackend.controller;

import com.restaurant.RestaurantReservationSystemBackend.dto.ReservationSlotDTO;
import com.restaurant.RestaurantReservationSystemBackend.service.ReservationSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations/slots")
@CrossOrigin
public class ReservationSlotController {

    @Autowired
    private ReservationSlotService reservationSlotService;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<String> saveReservationSlots(@PathVariable Long restaurantId, @RequestBody ReservationSlotDTO reservationSlotDTO) {
        reservationSlotDTO.setRestaurantId(restaurantId);
        reservationSlotService.saveReservationSlots(reservationSlotDTO);
        return ResponseEntity.ok("Reservation slots updated successfully!");
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ReservationSlotDTO> getReservationSlots(@PathVariable Long restaurantId) {
        ReservationSlotDTO slots = reservationSlotService.getReservationSlotsByRestaurantId(restaurantId);
        return ResponseEntity.ok(slots);
    }
}

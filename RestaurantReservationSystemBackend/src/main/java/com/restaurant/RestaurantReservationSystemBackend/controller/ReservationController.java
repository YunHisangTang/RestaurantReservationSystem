package com.restaurant.RestaurantReservationSystemBackend.controller;

import com.restaurant.RestaurantReservationSystemBackend.dto.ReservationDTO;
import com.restaurant.RestaurantReservationSystemBackend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/check-capacity")
    public ResponseEntity<Integer> checkCapacity(@RequestParam Long restaurantId,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationDate,
                                                 @RequestParam String reservationTime) {
        int availableCapacity = reservationService.getAvailableCapacity(restaurantId, reservationDate, reservationTime);
        return ResponseEntity.ok(availableCapacity);
    }

    @PostMapping
    public ResponseEntity<String> makeReservation(@RequestBody ReservationDTO reservationDTO) {
        int availableCapacity = reservationService.getAvailableCapacity(
                reservationDTO.getRestaurantId(),
                reservationDTO.getReservationDateTime().toLocalDate(),
                reservationDTO.getReservationDateTime().toLocalTime().toString()
        );
        int totalPeople = reservationDTO.getNumAdults() + reservationDTO.getNumChildren();
        if (availableCapacity < totalPeople) {
            return ResponseEntity.badRequest().body("Not enough capacity available.");
        }

        reservationService.saveReservation(reservationDTO);
        return ResponseEntity.ok("Reservation made successfully!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByRestaurantId(@PathVariable Long restaurantId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByRestaurantId(restaurantId);
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> updateReservationStatus(@PathVariable Long reservationId, @RequestBody ReservationDTO reservationDTO) {
        reservationService.updateReservationStatus(reservationId, reservationDTO.getStatus());
        return ResponseEntity.ok().build();
    }
}

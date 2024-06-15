package com.restaurant.RestaurantReservationSystemBackend.service;

import com.restaurant.RestaurantReservationSystemBackend.dto.ReservationDTO;
import com.restaurant.RestaurantReservationSystemBackend.model.Reservation;
import com.restaurant.RestaurantReservationSystemBackend.model.Restaurant;
import com.restaurant.RestaurantReservationSystemBackend.model.User;
import com.restaurant.RestaurantReservationSystemBackend.repository.ReservationRepository;
import com.restaurant.RestaurantReservationSystemBackend.repository.RestaurantRepository;
import com.restaurant.RestaurantReservationSystemBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public int getAvailableCapacity(Long restaurantId, LocalDate date, String time) {
        LocalTime reservationTime = LocalTime.parse(time);
        LocalDateTime startDateTime = LocalDateTime.of(date, reservationTime);
        LocalDateTime endDateTime = startDateTime.plusMinutes(1);

        List<Reservation> reservations = reservationRepository.findByRestaurantIdAndReservationDateTimeBetween(restaurantId, startDateTime, endDateTime)
                .stream()
                .filter(reservation -> !reservation.getStatus().equals("Deleted"))
                .collect(Collectors.toList());

        int reservedSeats = reservations.stream().mapToInt(r -> r.getNumAdults() + r.getNumChildren()).sum();
        int restaurantCapacity = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found")).getCapacity();

        return restaurantCapacity - reservedSeats;
    }

    public void saveReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setRestaurantId(reservationDTO.getRestaurantId());
        reservation.setReservationDateTime(reservationDTO.getReservationDateTime());
        reservation.setNumAdults(reservationDTO.getNumAdults());
        reservation.setNumChildren(reservationDTO.getNumChildren());
        reservation.setSameTable(reservationDTO.isSameTable());
        reservation.setStatus("Booked");

        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getReservationsByUserId(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserIdAndStatusNotOrderByReservationDateTimeAsc(userId, "Deleted");
        return reservations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        if (!reservation.getStatus().equals("Completed")) {
            reservation.setStatus("Deleted");
            reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Cannot delete a completed reservation");
        }
    }

    public List<ReservationDTO> getReservationsByRestaurantId(Long restaurantId) {
        List<Reservation> reservations = reservationRepository.findByRestaurantIdOrderByIdDesc(restaurantId);
        return reservations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void updateReservationStatus(Long reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        reservationRepository.save(reservation);
    }
    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUserId(reservation.getUserId());
        reservationDTO.setRestaurantId(reservation.getRestaurantId());
        reservationDTO.setReservationDateTime(reservation.getReservationDateTime());
        reservationDTO.setNumAdults(reservation.getNumAdults());
        reservationDTO.setNumChildren(reservation.getNumChildren());
        reservationDTO.setSameTable(reservation.isSameTable());
        reservationDTO.setStatus(reservation.getStatus());

        Restaurant restaurant = restaurantRepository.findById(reservation.getRestaurantId()).orElse(null);
        if (restaurant != null) {
            reservationDTO.setRestaurantName(restaurant.getName());
        }

        User user = userRepository.findById(reservation.getUserId()).orElse(null);
        if (user != null) {
            reservationDTO.setUserPhoneNumber(user.getPhoneNumber());
        }
        return reservationDTO;
    }
}

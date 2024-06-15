package com.restaurant.RestaurantReservationSystemBackend.service;

import com.restaurant.RestaurantReservationSystemBackend.dto.ReservationSlotDTO;
import com.restaurant.RestaurantReservationSystemBackend.model.ReservationSlot;
import com.restaurant.RestaurantReservationSystemBackend.repository.ReservationSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationSlotService {

    @Autowired
    private ReservationSlotRepository reservationSlotRepository;

    @Transactional
    public void saveReservationSlots(ReservationSlotDTO reservationSlotDTO) {
        ReservationSlot slot = reservationSlotRepository.findByRestaurantId(reservationSlotDTO.getRestaurantId())
                .orElse(new ReservationSlot());
        slot.setRestaurantId(reservationSlotDTO.getRestaurantId());
        slot.setTimes(reservationSlotDTO.getTimes());
        reservationSlotRepository.save(slot);
    }

    public ReservationSlotDTO getReservationSlotsByRestaurantId(Long restaurantId) {
        ReservationSlot slot = reservationSlotRepository.findByRestaurantId(restaurantId).orElse(new ReservationSlot());
        return new ReservationSlotDTO(slot.getRestaurantId(), slot.getTimes());
    }
}

package com.restaurant.RestaurantReservationSystemBackend.service;

import com.restaurant.RestaurantReservationSystemBackend.model.AdminRestaurant;
import com.restaurant.RestaurantReservationSystemBackend.model.Restaurant;
import com.restaurant.RestaurantReservationSystemBackend.repository.AdminRestaurantRepository;
import com.restaurant.RestaurantReservationSystemBackend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRestaurantService {

    @Autowired
    private AdminRestaurantRepository adminRestaurantRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    public List<Restaurant> findRestaurantsByAdminId(Long adminId) {
        List<AdminRestaurant> adminRestaurants = adminRestaurantRepository.findByAdminId(adminId);
        List<Long> restaurantIds = adminRestaurants.stream()
                .map(AdminRestaurant::getRestaurantId)
                .collect(Collectors.toList());
        return restaurantRepository.findByIdIn(restaurantIds);
    }
    public AdminRestaurant saveAdminRestaurant(AdminRestaurant adminRestaurant) {
        adminRestaurantRepository.save(adminRestaurant);
        return adminRestaurant;
    }

    public void deleteAdminRestaurantByRestaurantId(Long restaurantId) {
        adminRestaurantRepository.deleteByRestaurantId(restaurantId);
    }
}

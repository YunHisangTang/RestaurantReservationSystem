package com.restaurant.RestaurantReservationSystemBackend.service;

import com.restaurant.RestaurantReservationSystemBackend.dto.RestaurantDTO;
import com.restaurant.RestaurantReservationSystemBackend.model.AdminRestaurant;
import com.restaurant.RestaurantReservationSystemBackend.model.Restaurant;
import com.restaurant.RestaurantReservationSystemBackend.repository.AdminRestaurantRepository;
import com.restaurant.RestaurantReservationSystemBackend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AdminRestaurantRepository adminRestaurantRepository;

    @Autowired
    private AdminRestaurantService adminRestaurantService;

    public List<Restaurant> getRestaurantsByAdminId(Long adminId) {
        return adminRestaurantService.findRestaurantsByAdminId(adminId);
    }
    public Restaurant addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setLatitude(restaurantDTO.getLatitude());
        restaurant.setLongitude(restaurantDTO.getLongitude());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setMenu(restaurantDTO.getMenu());
        restaurant.setOpeningTime(restaurantDTO.getOpeningTime());
        restaurant.setClosingTime(restaurantDTO.getClosingTime());
        restaurant.setDiningDuration(restaurantDTO.getDiningDuration());
        restaurant.setCapacity(restaurantDTO.getCapacity());
        Restaurant newRestaurant = restaurantRepository.save(restaurant);

        return newRestaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            restaurant.setName(restaurantDTO.getName());
            restaurant.setAddress(restaurantDTO.getAddress());
            restaurant.setLatitude(restaurantDTO.getLatitude());
            restaurant.setLongitude(restaurantDTO.getLongitude());
            restaurant.setDescription(restaurantDTO.getDescription());
            restaurant.setMenu(restaurantDTO.getMenu());
            restaurant.setOpeningTime(restaurantDTO.getOpeningTime());
            restaurant.setClosingTime(restaurantDTO.getClosingTime());
            restaurant.setDiningDuration(restaurantDTO.getDiningDuration());
            restaurant.setCapacity(restaurantDTO.getCapacity());
            return restaurantRepository.save(restaurant);
        }
        return null;
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

}

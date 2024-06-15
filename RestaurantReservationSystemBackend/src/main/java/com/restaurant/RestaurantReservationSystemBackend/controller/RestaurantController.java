package com.restaurant.RestaurantReservationSystemBackend.controller;

import com.restaurant.RestaurantReservationSystemBackend.dto.RestaurantDTO;
import com.restaurant.RestaurantReservationSystemBackend.model.Admin;
import com.restaurant.RestaurantReservationSystemBackend.model.AdminRestaurant;
import com.restaurant.RestaurantReservationSystemBackend.model.Restaurant;
import com.restaurant.RestaurantReservationSystemBackend.repository.RestaurantRepository;
import com.restaurant.RestaurantReservationSystemBackend.service.AdminRestaurantService;
import com.restaurant.RestaurantReservationSystemBackend.service.AdminService;
import com.restaurant.RestaurantReservationSystemBackend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
@CrossOrigin
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private AdminRestaurantService adminRestaurantService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/add")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminEmail = authentication.getName();
        Admin admin = adminService.getAdminByEmail(adminEmail);

        if (admin == null) {
            return ResponseEntity.status(403).body(null); // Or handle the error appropriately
        }

        Restaurant newRestaurant = restaurantService.addRestaurant(restaurantDTO);
        AdminRestaurant adminRestaurant = new AdminRestaurant();
        adminRestaurant.setAdminId(admin.getId());
        adminRestaurant.setRestaurantId(newRestaurant.getId());
        adminRestaurantService.saveAdminRestaurant(adminRestaurant);

        return ResponseEntity.ok(newRestaurant);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByAdminId(@PathVariable Long adminId) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByAdminId(adminId);
        return ResponseEntity.ok(restaurants);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam(required = false) String query) {
        List<Restaurant> restaurants;
        if (query == null || query.isEmpty()) {
            restaurants = restaurantRepository.findAll();
        } else {
            restaurants = restaurantRepository.findByNameContainingIgnoreCase(query);
        }
        return ResponseEntity.ok(restaurants);
    }
}

package com.restaurant.RestaurantReservationSystemBackend.controller;

import com.restaurant.RestaurantReservationSystemBackend.dto.AdminDTO;
import com.restaurant.RestaurantReservationSystemBackend.dto.AdminResponse;
import com.restaurant.RestaurantReservationSystemBackend.model.Admin;
import com.restaurant.RestaurantReservationSystemBackend.model.AdminRestaurant;
import com.restaurant.RestaurantReservationSystemBackend.model.Restaurant;
import com.restaurant.RestaurantReservationSystemBackend.service.AdminService;
import com.restaurant.RestaurantReservationSystemBackend.service.AdminRestaurantService;
import com.restaurant.RestaurantReservationSystemBackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRestaurantService adminRestaurantService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminDTO adminDTO) {
        if (adminService.emailExists(adminDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        adminService.registerAdmin(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminDTO adminDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(adminDTO.getEmail(), adminDTO.getPassword())
            );
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body("User disabled");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        final UserDetails userDetails = adminService.loadUserByUsername(adminDTO.getEmail());
        final String token = jwtUtil.generateToken(userDetails);

        Admin admin = adminService.getAdminByEmail(adminDTO.getEmail());

        AdminResponse response = new AdminResponse(
                token,
                admin.getId(),
                admin.getPhoneNumber(),
                admin.getName(),
                admin.getEmail(),
                admin.getPoints());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Admin admin = (Admin) authentication.getPrincipal();
            return ResponseEntity.ok(admin);
        }
        return ResponseEntity.status(401).body("No admin is currently authenticated");
    }

    @GetMapping("/restaurants/{adminId}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByAdminId(@PathVariable Long adminId) {
        List<Restaurant> restaurants = adminRestaurantService.findRestaurantsByAdminId(adminId);
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping
    public ResponseEntity<AdminRestaurant> addAdminRestaurant(@RequestBody AdminRestaurant adminRestaurant) {
        AdminRestaurant savedAdminRestaurant = adminRestaurantService.saveAdminRestaurant(adminRestaurant);
        return ResponseEntity.ok(savedAdminRestaurant);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteAdminRestaurantByRestaurantId(@PathVariable Long restaurantId) {
        adminRestaurantService.deleteAdminRestaurantByRestaurantId(restaurantId);
        return ResponseEntity.noContent().build();
    }
}

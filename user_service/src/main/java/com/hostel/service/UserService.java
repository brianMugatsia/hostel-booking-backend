package com.hostel.service;

import com.hostel.dto.UserDTO;
import com.hostel.entity.Role;
import com.hostel.entity.User;
import com.hostel.repository.UserRepository;
import com.hostel.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user.
     * Validates fields based on role (STUDENT or OWNER)
     */
    public User register(UserDTO userDTO) {
        // Check if email already exists
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Validate required fields for OWNER role
        if (userDTO.getRole().equalsIgnoreCase("OWNER")) {
            if (userDTO.getPhone() == null || userDTO.getPhone().isBlank()) {
                throw new RuntimeException("Phone is required for owners");
            }
            if (userDTO.getHostelName() == null || userDTO.getHostelName().isBlank() ||
                    userDTO.getHostelNumber() == null || userDTO.getHostelNumber().isBlank()) {
                throw new RuntimeException("Hostel name and number are required for owners");
            }
        }

        // Create User entity
        User user = new User();
        user.setName(userDTO.getName());
        user.setOtherNames(userDTO.getOtherNames());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
        user.setPhone(userDTO.getPhone());
        user.setHostelName(userDTO.getHostelName());
        user.setHostelNumber(userDTO.getHostelNumber());

        return userRepository.save(user);
    }

    /**
     * Logs in a user and generates JWT token
     */
    public LoginResponse login(UserDTO userDTO) {
        // Find user by email
        User existingUser = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(userDTO.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(existingUser.getEmail());

        // Hide password
        existingUser.setPassword(null);

        return new LoginResponse(token, existingUser);
    }

    /**
     * DTO for login response
     */
    public static class LoginResponse {
        private final String token;
        private final User user;

        public LoginResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public User getUser() {
            return user;
        }
    }
}
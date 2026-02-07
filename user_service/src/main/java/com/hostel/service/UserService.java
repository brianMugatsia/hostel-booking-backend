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

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDTO userDTO) {
        // Check if email already exists
        Optional<User> existing = userRepository.findByEmail(userDTO.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));

        return userRepository.save(user);
    }

    public User login(UserDTO userDTO) {
        User existingUser = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(userDTO.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Optionally generate JWT token here
        String token = JwtUtil.generateToken(existingUser.getEmail());

        existingUser.setPassword(null); // don’t expose password
        return existingUser;
    }
}

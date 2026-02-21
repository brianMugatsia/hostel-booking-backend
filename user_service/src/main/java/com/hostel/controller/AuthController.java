package com.hostel.controller;

import com.hostel.dto.UserDTO;
import com.hostel.entity.User;
import com.hostel.service.UserService;
import com.hostel.service.UserService.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173") // Allow React frontend
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /** Register endpoint */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            User newUser = userService.register(userDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully!");
            response.put("user", newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /** Login endpoint */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO userDTO) {
        try {
            LoginResponse loginResponse = userService.login(userDTO);
            User loggedInUser = loginResponse.getUser();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("token", loginResponse.getToken());
            response.put("name", loggedInUser.getName());
            response.put("email", loggedInUser.getEmail());
            response.put("role", loggedInUser.getRole().name());

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /** Optional test endpoint */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth API is working!");
    }

    /** Handle validation errors for @Valid */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
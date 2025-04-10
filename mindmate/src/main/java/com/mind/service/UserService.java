
// UserService.java
package com.mind.service;

import com.mind.dto.*;
import com.mind.collection.User;
import com.mind.repository.UserRepository;
import com.mind.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> register(RegistRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .emergencyContact(request.getEmergencyContact())
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully with ID: " + savedUser.getId());
    }

    public ResponseEntity<?> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponse(token, "Login successful"));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
} 

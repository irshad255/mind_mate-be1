
// UserController.java
package com.mind.controller;

import com.mind.dto.*;
import com.mind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello world";
    }
}
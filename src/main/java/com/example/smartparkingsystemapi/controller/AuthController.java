package com.example.smartparkingsystemapi.controller;

import com.example.smartparkingsystemapi.dto.LoginRequest;
import com.example.smartparkingsystemapi.dto.RegisterRequest;
import com.example.smartparkingsystemapi.entity.User;
import com.example.smartparkingsystemapi.service.AuthServices;
import com.example.smartparkingsystemapi.wrapper.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServices authServices;

    public AuthController(AuthServices authServices){
        this.authServices = authServices;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(
            @RequestBody RegisterRequest request
            ){
        System.out.println("Controller hit");
        User user = authServices.registerUser(request);
        ApiResponse<User> response = new ApiResponse<User>("User registered",user);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<User>> loginUser(
//            @RequestBody LoginRequest request
//            ){
//        System.out.println("Login hit");
//        User user = authServices.loginUser(request);
//        ApiResponse<User> response = new ApiResponse<>("Login successful",user);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginRequest request) {
        try {
            System.out.println("LOGIN HIT");
            String token = authServices.loginUser(request);
            return ResponseEntity.ok(new ApiResponse<>("Login successful", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(e.getMessage(),"Login failed"));
        }
    }
}

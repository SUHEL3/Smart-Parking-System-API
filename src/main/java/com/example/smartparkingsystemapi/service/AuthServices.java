package com.example.smartparkingsystemapi.service;

import com.example.smartparkingsystemapi.JWTSecurity.JwtUtil;
import com.example.smartparkingsystemapi.dto.LoginRequest;
import com.example.smartparkingsystemapi.dto.RegisterRequest;
import com.example.smartparkingsystemapi.entity.User;
import com.example.smartparkingsystemapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServices(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(RegisterRequest userDTO){
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(u->{
                    throw new RuntimeException("User already exists");
                        }
                );
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        //Password Encryption
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public String loginUser(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
            return jwtUtil.generateToken(user.getEmail());
    }
}

package com.example.smartparkingsystemapi.repository;

import com.example.smartparkingsystemapi.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP,Long> {

}

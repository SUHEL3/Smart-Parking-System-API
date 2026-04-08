package com.example.smartparkingsystemapi.dto;

import com.example.smartparkingsystemapi.entity.model.VehicleType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RequestParking {
    @NotBlank(message = "Vehicle number is mandatory")
    private String vehicleNumber;
    private VehicleType vehicleType;

    private Long phoneNumber;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}

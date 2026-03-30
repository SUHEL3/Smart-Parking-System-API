package com.example.smartparkingsystemapi.dto;

import com.example.smartparkingsystemapi.entity.model.VehicleType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RequestParking {
    private String vehicleNumber;
    private VehicleType vehicleType;
    @Min(value = 1,message = "At least 10 digit")
    @Max(value = 10,message = "At most 10 digits")
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

package com.example.smartparkingsystemapi.entity;

import com.example.smartparkingsystemapi.entity.model.SlotStatus;
import com.example.smartparkingsystemapi.entity.model.VehicleType;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_slot")
public class ParkingSlot {
    @Id
    private Long id;

    private String slotNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus;

    //Constructor
    public ParkingSlot(){

    }

    public ParkingSlot(Long id, String slotNumber, VehicleType vehicleType, SlotStatus slotStatus) {
        this.id = id;
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
        this.slotStatus = slotStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
    }
}



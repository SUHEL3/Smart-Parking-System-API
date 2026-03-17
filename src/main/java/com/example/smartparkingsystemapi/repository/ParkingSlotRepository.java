package com.example.smartparkingsystemapi.repository;

import com.example.smartparkingsystemapi.entity.ParkingSlot;
import com.example.smartparkingsystemapi.entity.model.SlotStatus;
import com.example.smartparkingsystemapi.entity.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    Optional<ParkingSlot> findFirstByVehicleTypeAndSlotStatusOrderBySlotNumberAsc(
            VehicleType vehicleType,
            SlotStatus status
    );
}

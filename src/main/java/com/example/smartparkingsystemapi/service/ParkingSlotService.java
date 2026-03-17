package com.example.smartparkingsystemapi.service;

import com.example.smartparkingsystemapi.entity.ParkingSlot;
import com.example.smartparkingsystemapi.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;


    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public List<ParkingSlot> getAllSlots(){
        return parkingSlotRepository.findAll();
    }
}

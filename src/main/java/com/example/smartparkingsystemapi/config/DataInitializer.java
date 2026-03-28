package com.example.smartparkingsystemapi.config;

import com.example.smartparkingsystemapi.entity.ParkingSlot;
import com.example.smartparkingsystemapi.entity.model.SlotStatus;
import com.example.smartparkingsystemapi.entity.model.VehicleType;
import com.example.smartparkingsystemapi.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(ParkingSlotRepository repository){
       return args -> {
           if(repository.count() == 0){
               List<ParkingSlot> pl = new ArrayList<>();
               for(int i = 1; i <= 60 ;i++){
                    ParkingSlot newSlot = new ParkingSlot();
                    newSlot.setSlotNumber("SLOT-"+i);
                    newSlot.setSlotStatus(SlotStatus.available);
                    if(i>30){
                        newSlot.setVehicleType(VehicleType.bike);
                    }else {
                        newSlot.setVehicleType(VehicleType.car);
                    }
                    pl.add(newSlot);
               }
               repository.saveAll(pl);
               System.out.println("Parking Slots initialized.");
           }else {
               System.out.println("Slots already exist, skipping......");
           }
       };
    }
}

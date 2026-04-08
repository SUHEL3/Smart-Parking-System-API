package com.example.smartparkingsystemapi.service;

import com.example.smartparkingsystemapi.entity.ParkingSlot;
import com.example.smartparkingsystemapi.entity.Ticket;
import com.example.smartparkingsystemapi.entity.model.SlotStatus;
import com.example.smartparkingsystemapi.entity.model.VehicleType;
import com.example.smartparkingsystemapi.repository.ParkingSlotRepository;
import com.example.smartparkingsystemapi.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public TicketService(TicketRepository ticketRepository,ParkingSlotRepository parkingSlotRepository){
        this.ticketRepository = ticketRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    @Transactional
    public Ticket parkVehicle(String vehicleNumber, VehicleType vehicleType, Long phoneNumber){
        ticketRepository.findByVehicleNumberAndExitTimeIsNull(vehicleNumber)
                .ifPresent(t -> {
                            throw new RuntimeException("Vehicle already parked!"+t.getVehicleNumber());
                   }
                );

        ParkingSlot parkingSlot = parkingSlotRepository.findFirstByVehicleTypeAndSlotStatusOrderBySlotNumberAsc(
                vehicleType, SlotStatus.available
        )
                .orElseThrow(()-> new RuntimeException("Parking Slot not available!"));

        parkingSlot.setSlotStatus(SlotStatus.occupied);
        parkingSlotRepository.save(parkingSlot);

        Ticket ticket = new Ticket();
        ticket.setVehicleNumber(vehicleNumber);
        ticket.setVehicleType(vehicleType);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setParkingSlot(parkingSlot);
        ticket.setPhoneNumber(phoneNumber);

        return ticketRepository.save(ticket);
    }
    @Transactional
    public Ticket exitVehicle(long id){
        Ticket newTicket = ticketRepository.findById(id).orElseThrow(()-> new RuntimeException("Ticket not found."));
        if(newTicket.getExitTime() != null){
            throw new RuntimeException("Vehicle already exited.");
        }
        newTicket.setExitTime(LocalDateTime.now());

        ParkingSlot slot = newTicket.getParkingSlot();
        slot.setSlotStatus(SlotStatus.available);
        parkingSlotRepository.save(slot);

        return ticketRepository.save(newTicket);
    }

    LocalDate date = LocalDate.now();
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime startOfNextDay = date.plusDays(1).atStartOfDay();

    public List<Ticket> getReport(){
        return ticketRepository.findByEntryTimeBetweenOrExitTimeBetween(startOfDay,startOfNextDay,
                startOfDay,startOfNextDay
        );
    }
}

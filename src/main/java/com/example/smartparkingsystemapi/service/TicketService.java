package com.example.smartparkingsystemapi.service;

import com.example.smartparkingsystemapi.entity.OTP;
import com.example.smartparkingsystemapi.entity.ParkingSlot;
import com.example.smartparkingsystemapi.entity.Ticket;
import com.example.smartparkingsystemapi.entity.model.SlotStatus;
import com.example.smartparkingsystemapi.entity.model.VehicleType;
import com.example.smartparkingsystemapi.repository.OTPRepository;
import com.example.smartparkingsystemapi.repository.ParkingSlotRepository;
import com.example.smartparkingsystemapi.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final OTPRepository otpRepository;

    public TicketService(TicketRepository ticketRepository,ParkingSlotRepository parkingSlotRepository,OTPRepository otpRepository){
        this.ticketRepository = ticketRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.otpRepository = otpRepository;
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
    public int exitVehicle(long id){
        Ticket newTicket = ticketRepository.findById(id).orElseThrow(()-> new RuntimeException("Ticket not found."));
        if(newTicket.getExitTime() != null){
            throw new RuntimeException("Vehicle already exited.");
        }
        newTicket.setExitTime(LocalDateTime.now());

        //Amount calculation
        long second = Duration.between(newTicket.getEntryTime(),LocalDateTime.now()).getSeconds();
        double hours = Math.ceil(second/3600.0);
        double amount = (hours >= 2) ? hours*10 : 2*10+(hours-2)*5;
        newTicket.setAmount(amount);

        ParkingSlot slot = newTicket.getParkingSlot();
        slot.setSlotStatus(SlotStatus.available);
        parkingSlotRepository.save(slot);

        //OTP creation
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        long expiry = System.currentTimeMillis() + (2 * 60 * 1000);

        OTP newotp = new OTP(newTicket.getId(),expiry,3,otp);
        otpRepository.save(newotp);

        return otp;
    }

    public boolean verifyOtp(long id, int otp){
        OTP existingOtp = otpRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Invalid Id.")
        );
        if (existingOtp.getOtp() == otp){
            long currTime = System.currentTimeMillis();
            long expiryTime = existingOtp.getExpierytime();
            otpRepository.deleteById(id);
            return currTime <= expiryTime;
        }else {
            return false;
        }
    }

    LocalDate date = LocalDate.now();
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime startOfNextDay = date.plusDays(1).atStartOfDay();

    public List<Ticket> getReport(){
        return ticketRepository.findByEntryTimeBetweenOrExitTimeBetween(startOfDay,startOfNextDay,
                startOfDay,startOfNextDay
        );
    }

    public Double getTodayRevenue(){
        return Optional.ofNullable(ticketRepository.getTodayRevenue()).orElse(0.0);
    }

    public Double getMonthRevenue(){
        return Optional.ofNullable(ticketRepository.getMonthlyRevenue()).orElse(0.0);
    }

    public Double getRevenue(int month,int year){
        return Optional.ofNullable(ticketRepository.getRevenue(month,year)).orElse(0.0);
    }
}

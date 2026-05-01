package com.example.smartparkingsystemapi.controller;

import com.example.smartparkingsystemapi.dto.RequestParking;
import com.example.smartparkingsystemapi.entity.Ticket;
import com.example.smartparkingsystemapi.repository.TicketRepository;
import com.example.smartparkingsystemapi.service.TicketService;
import com.example.smartparkingsystemapi.wrapper.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/getAllTicket")
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @PostMapping("/park")
    public ResponseEntity<ApiResponse<Ticket>> parkVehicle(@Valid @RequestBody RequestParking request){
        Ticket ticket = ticketService.parkVehicle(request.getVehicleNumber(),request.getVehicleType(),request.getPhoneNumber());
        ApiResponse<Ticket> response = new ApiResponse<>("Vehicle parked",ticket);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/exit/{id}")
    public ResponseEntity<ApiResponse<Integer>> exitVehicle(@PathVariable long id){
         int ticket = ticketService.exitVehicle(id);
         ApiResponse<Integer> response = new ApiResponse<>("OTP :",ticket);
         return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyOTP(@RequestParam long id,
                                                          @RequestParam int otp){
        boolean verified = ticketService.verifyOtp(id,otp);
        ApiResponse<Boolean> response = new ApiResponse<>("Verified :", verified);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/report")
    public ResponseEntity<ApiResponse<List<Ticket>>> getReport(){
        List<Ticket> report = ticketService.getReport();
        ApiResponse<List<Ticket>> response = new ApiResponse<>("Report fetched",report);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/todayrevenue")
    public ResponseEntity<ApiResponse<Double>> getTodayRevenue(){
        Double amount = ticketService.getTodayRevenue();
        ApiResponse<Double> response = new ApiResponse<>("Today's Revenue",amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthlyrevenue")
    public ResponseEntity<ApiResponse<Double>> getMonthlyRevenue(){
        Double amount = ticketService.getMonthRevenue();
        ApiResponse<Double> response = new ApiResponse<>("Today's Revenue",amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getrevenue")
    public ResponseEntity<ApiResponse<Double>> getRevenue(@RequestParam int month,
                                                          @RequestParam int year){
        Double amount = ticketService.getRevenue(month,year);
        ApiResponse<Double> response= new ApiResponse<>("Revenue for :"+month+"/"+year,amount);
        return ResponseEntity.ok(response);
    }
}

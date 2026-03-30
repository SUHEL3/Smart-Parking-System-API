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
        ApiResponse<Ticket> response = new ApiResponse<Ticket>("Vehicle parked",ticket);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/exit/{id}")
    public ResponseEntity<ApiResponse<Ticket>> exitVehicle(@PathVariable long id){
         Ticket ticket = ticketService.exitVehicle(id);
         ApiResponse<Ticket> response = new ApiResponse<>("Vehicle exited",ticket);
         return ResponseEntity.status(200).body(response);
    }
}

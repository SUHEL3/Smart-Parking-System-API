package com.example.smartparkingsystemapi.controller;

import com.example.smartparkingsystemapi.dto.RequestParking;
import com.example.smartparkingsystemapi.entity.Ticket;
import com.example.smartparkingsystemapi.repository.TicketRepository;
import com.example.smartparkingsystemapi.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @PostMapping("/park")
    public Ticket parkVehicle(@RequestBody RequestParking request){
        return ticketService.parkVehicle(request.getVehicleNumber(),request.getVehicleType());
    }

}

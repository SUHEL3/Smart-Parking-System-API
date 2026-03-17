package com.example.smartparkingsystemapi.repository;

import com.example.smartparkingsystemapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehicleNumberAndExitTimeIsNull(String vehicleNumber);
}

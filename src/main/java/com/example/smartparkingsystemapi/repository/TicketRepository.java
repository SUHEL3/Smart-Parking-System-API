package com.example.smartparkingsystemapi.repository;

import com.example.smartparkingsystemapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehicleNumberAndExitTimeIsNull(String vehicleNumber);

    List<Ticket> findByEntryTimeBetweenOrExitTimeBetween(
            LocalDateTime start1,LocalDateTime end1,
            LocalDateTime start2,LocalDateTime end2
    );
}

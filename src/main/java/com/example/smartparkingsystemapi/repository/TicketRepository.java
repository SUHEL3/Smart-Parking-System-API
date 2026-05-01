package com.example.smartparkingsystemapi.repository;

import com.example.smartparkingsystemapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT SUM(t.amount) FROM Ticket t " +
            "WHERE DATE(t.exitTime) = CURRENT_DATE " +
            "AND t.exitTime IS NOT NULL")
    Double getTodayRevenue();

    @Query("SELECT SUM(t.amount) FROM Ticket t " +
            "WHERE MONTH(t.exitTime) = MONTH(CURRENT_DATE) " +
            "AND YEAR(t.exitTime) = YEAR(CURRENT_DATE) " +
            "AND t.exitTime IS NOT NULL")
    Double getMonthlyRevenue();

    @Query("SELECT SUM(t.amount) FROM Ticket t " +
    "WHERE MONTH(t.exitTime) = :month " +
    "AND YEAR(t.exitTime) = :year ")
    Double getRevenue(@Param("month") Integer month,
                      @Param("year") Integer year);

}

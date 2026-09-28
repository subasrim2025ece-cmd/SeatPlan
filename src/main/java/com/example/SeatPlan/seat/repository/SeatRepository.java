package com.example.SeatPlan.seat.repository;

import com.example.SeatPlan.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}

        package com.example.SeatPlan.booking.repository;

import com.example.SeatPlan.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByHallIdAndStatus(
            Long hallId,
            String status
    );

    Optional<Booking> findBySeatId(
            Long seatId
    );
}

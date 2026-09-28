package com.example.SeatPlan.seat.service;

import com.example.SeatPlan.seat.entity.Seat;
import com.example.SeatPlan.seat.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    public Seat updateSeat(Long id, Seat seat) {

        Seat existingSeat = getSeatById(id);

        existingSeat.setSeatNumber(seat.getSeatNumber());
        existingSeat.setRowNumber(seat.getRowNumber());
        existingSeat.setColumnNumber(seat.getColumnNumber());
        existingSeat.setStatus(seat.getStatus());
        existingSeat.setHallId(seat.getHallId());

        return seatRepository.save(existingSeat);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }
}

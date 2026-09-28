package com.example.SeatPlan.hall.service;

import com.example.SeatPlan.hall.entity.Hall;
import com.example.SeatPlan.hall.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    // CREATE
    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

    // GET ALL
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    // GET BY ID
    public Hall getHallById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with ID: " + id));
    }

    // UPDATE
    public Hall updateHall(Long id, Hall hall) {

        Hall existingHall = hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with ID: " + id));

        existingHall.setHallName(hall.getHallName());
        existingHall.setRowCount(hall.getRowCount());
        existingHall.setColumns(hall.getColumns());
        existingHall.setCapacity(hall.getCapacity());

        return hallRepository.save(existingHall);
    }

    // DELETE
    public void deleteHall(Long id) {

        Hall existingHall = hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with ID: " + id));

        hallRepository.delete(existingHall);
    }
}
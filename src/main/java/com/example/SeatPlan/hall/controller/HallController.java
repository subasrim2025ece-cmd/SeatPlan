package com.example.SeatPlan.hall.controller;

import com.example.SeatPlan.hall.entity.Hall;
import com.example.SeatPlan.hall.service.HallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    // POST - CREATE
    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return hallService.createHall(hall);
    }

    // GET - ALL HALLS
    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.getAllHalls();
    }

    // GET - HALL BY ID
    @GetMapping("/{id}")
    public Hall getHallById(@PathVariable Long id) {
        return hallService.getHallById(id);
    }

    // PUT - UPDATE
    @PutMapping("/{id}")
    public Hall updateHall(
            @PathVariable Long id,
            @RequestBody Hall hall) {

        return hallService.updateHall(id, hall);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteHall(@PathVariable Long id) {

        hallService.deleteHall(id);

        return "Hall deleted successfully";
    }
}
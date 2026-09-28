package com.example.SeatPlan.booking.controller;

import com.example.SeatPlan.booking.entity.Booking;
import com.example.SeatPlan.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // POST - Create booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    // GET - Fetch all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // GET - Fetch booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {

        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT - Update booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @RequestBody Booking booking) {

        Booking updatedBooking =
                bookingService.updateBooking(id, booking);

        if (updatedBooking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedBooking);
    }

    // DELETE - Delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.noContent().build();
    }
}
package com.example.SeatPlan.booking.service;

import com.example.SeatPlan.booking.entity.Booking;
import com.example.SeatPlan.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // CREATE
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // FETCH ALL
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // FETCH BY ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // UPDATE
    public Booking updateBooking(Long id, Booking booking) {

        Optional<Booking> existingBooking =
                bookingRepository.findById(id);

        if (existingBooking.isPresent()) {

            Booking existing = existingBooking.get();

            existing.setStudentId(booking.getStudentId());
            existing.setSeatId(booking.getSeatId());
            existing.setHallId(booking.getHallId());
            existing.setBookingDate(booking.getBookingDate());
            existing.setStatus(booking.getStatus());

            return bookingRepository.save(existing);
        }

        return null;
    }

    // DELETE
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
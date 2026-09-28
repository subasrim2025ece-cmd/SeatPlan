
        package com.example.SeatPlan.booking.service;

import com.example.SeatPlan.booking.entity.Booking;
import com.example.SeatPlan.booking.repository.BookingRepository;
import com.example.SeatPlan.hall.entity.Hall;
import com.example.SeatPlan.hall.service.HallService;
import com.example.SeatPlan.seat.entity.Seat;
import com.example.SeatPlan.seat.service.SeatService;
import com.example.SeatPlan.student.entity.Student;
import com.example.SeatPlan.student.service.StudentService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final StudentService studentService;
    private final HallService hallService;

    public BookingService(
            BookingRepository bookingRepository,
            SeatService seatService,
            StudentService studentService,
            HallService hallService) {

        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.studentService = studentService;
        this.hallService = hallService;
    }

    // CREATE BOOKING
    public Booking createBooking(Booking booking) {

        if (booking.getStudentId() == null) {
            throw new RuntimeException("Student ID is required");
        }

        if (booking.getHallId() == null) {
            throw new RuntimeException("Hall ID is required");
        }

        // Get hall
        Hall hall = hallService.getHallById(
                booking.getHallId()
        );

        // Get student
        Student student = studentService.getStudentById(
                booking.getStudentId()
        );

        // Validate class
        if (student.getClassName() == null
                || student.getClassName().isBlank()) {

            throw new RuntimeException(
                    "Student class is required"
            );
        }

        // Validate section
        if (student.getSection() == null
                || student.getSection().isBlank()) {

            throw new RuntimeException(
                    "Student section is required"
            );
        }

        // Get seats of selected hall
        List<Seat> seats = seatService.getSeatsByHall(
                booking.getHallId()
        );

        if (seats.isEmpty()) {
            throw new RuntimeException(
                    "No seats found in this hall"
            );
        }

        // ==========================================
        // CONSTRAINT 1: HALL CAPACITY
        // ==========================================

        long bookedCount =
                bookingRepository.findByHallIdAndStatus(
                        booking.getHallId(),
                        "BOOKED"
                ).size();

        if (bookedCount >= hall.getCapacity()) {

            throw new RuntimeException(
                    "Hall capacity exceeded. Maximum capacity is "
                            + hall.getCapacity()
            );
        }

        // ==========================================
        // FIND SAFE SEAT
        // ==========================================

        Seat selectedSeat = null;

        for (Seat seat : seats) {

            if (!"AVAILABLE".equalsIgnoreCase(
                    seat.getStatus())) {

                continue;
            }

            if (isSeatSafe(
                    seat,
                    seats,
                    student.getClassName(),
                    student.getSection())) {

                selectedSeat = seat;
                break;
            }
        }

        // ==========================================
        // IF NO SAFE SEAT, USE ANY AVAILABLE SEAT
        // ==========================================

        if (selectedSeat == null) {

            for (Seat seat : seats) {

                if ("AVAILABLE".equalsIgnoreCase(
                        seat.getStatus())) {

                    selectedSeat = seat;
                    break;
                }
            }
        }

        if (selectedSeat == null) {

            throw new RuntimeException(
                    "No available seat in this hall"
            );
        }

        // ==========================================
        // BOOK SEAT
        // ==========================================

        selectedSeat.setStatus("BOOKED");

        seatService.updateSeat(
                selectedSeat.getSeatId(),
                selectedSeat
        );

        booking.setSeatId(
                selectedSeat.getSeatId()
        );

        booking.setBookingDate(
                LocalDate.now().toString()
        );

        booking.setStatus("BOOKED");

        return bookingRepository.save(booking);
    }

    // ==========================================
    // CHECK ADJACENT SEATS
    // ==========================================

    private boolean isSeatSafe(
            Seat seat,
            List<Seat> seats,
            String className,
            String section) {

        int row = seat.getRowNumber();
        int column = seat.getColumnNumber();

        int[][] directions = {
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, -1},
                {0, 1},
                {1, -1},
                {1, 0},
                {1, 1}
        };

        for (int[] direction : directions) {

            int neighbourRow =
                    row + direction[0];

            int neighbourColumn =
                    column + direction[1];

            for (Seat neighbour : seats) {

                if (neighbour.getRowNumber()
                        == neighbourRow
                        &&
                        neighbour.getColumnNumber()
                                == neighbourColumn
                        &&
                        "BOOKED".equalsIgnoreCase(
                                neighbour.getStatus())) {

                    Optional<Booking> neighbourBooking =
                            bookingRepository.findBySeatId(
                                    neighbour.getSeatId()
                            );

                    if (neighbourBooking.isEmpty()) {
                        continue;
                    }

                    Booking existingBooking =
                            neighbourBooking.get();

                    try {

                        Student neighbourStudent =
                                studentService.getStudentById(
                                        existingBooking.getStudentId()
                                );

                        boolean sameClass =
                                className.equalsIgnoreCase(
                                        neighbourStudent.getClassName()
                                );

                        boolean sameSection =
                                section.equalsIgnoreCase(
                                        neighbourStudent.getSection()
                                );

                        if (sameClass && sameSection) {
                            return false;
                        }

                    } catch (RuntimeException ignored) {
                        // Ignore missing student
                    }
                }
            }
        }

        return true;
    }

    // ==========================================
    // GET ALL BOOKINGS
    // ==========================================

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // ==========================================
    // GET BOOKING BY ID
    // ==========================================

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // ==========================================
    // UPDATE BOOKING
    // ==========================================

    public Booking updateBooking(
            Long id,
            Booking booking) {

        Optional<Booking> existing =
                bookingRepository.findById(id);

        if (existing.isEmpty()) {
            return null;
        }

        Booking current = existing.get();

        current.setStudentId(
                booking.getStudentId()
        );

        current.setSeatId(
                booking.getSeatId()
        );

        current.setHallId(
                booking.getHallId()
        );

        current.setBookingDate(
                booking.getBookingDate()
        );

        current.setStatus(
                booking.getStatus()
        );

        return bookingRepository.save(current);
    }

    // ==========================================
    // DELETE BOOKING
    // ==========================================

    public void deleteBooking(Long id) {

        if (!bookingRepository.existsById(id)) {

            throw new RuntimeException(
                    "Booking not found with ID: " + id
            );
        }

        bookingRepository.deleteById(id);
    }
}

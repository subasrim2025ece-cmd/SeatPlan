document.addEventListener("DOMContentLoaded", loadBookings);

async function createBooking() {

    const studentId =
        document.getElementById("studentId").value;

    const hallId =
        document.getElementById("hallId").value;

    if (!studentId || !hallId) {

        document.getElementById("bookingMessage").innerText =
            "Please enter Student ID and Hall ID.";

        document.getElementById("bookingMessage")
            .className = "error";

        return;
    }

    const booking = {

        studentId: Number(studentId),

        hallId: Number(hallId)
    };

    try {

        const result =
            await apiRequest("/api/bookings", {

                method: "POST",

                body: JSON.stringify(booking)
            });

        document.getElementById("bookingMessage").innerText =
            "Seat allocated successfully.";

        document.getElementById("bookingMessage")
            .className = "success";

        showBookingResult(result);

        loadBookings();

    } catch (error) {

        document.getElementById("bookingMessage").innerText =
            error.message;

        document.getElementById("bookingMessage")
            .className = "error";
    }
}


function showBookingResult(booking) {

    const result =
        document.getElementById("bookingResult");

    result.className = "booking-result";

    result.innerHTML = `
        <h3>Seat Allocated Successfully</h3>

        <p>
            <strong>Booking ID:</strong>
            ${booking.bookingId}
        </p>

        <p>
            <strong>Student ID:</strong>
            ${booking.studentId}
        </p>

        <p>
            <strong>Hall ID:</strong>
            ${booking.hallId}
        </p>

        <p>
            <strong>Seat ID:</strong>
            ${booking.seatId}
        </p>

        <p>
            <strong>Date:</strong>
            ${booking.bookingDate}
        </p>

        <p>
            <strong>Status:</strong>
            ${booking.status}
        </p>
    `;
}


async function loadBookings() {

    try {

        const bookings =
            await apiRequest("/api/bookings");

        const tableBody =
            document.getElementById("bookingTableBody");

        tableBody.innerHTML = "";

        bookings.forEach(booking => {

            const row =
                document.createElement("tr");

            row.innerHTML = `
                <td>${booking.bookingId}</td>
                <td>${booking.studentId}</td>
                <td>${booking.hallId}</td>
                <td>${booking.seatId}</td>
                <td>${booking.bookingDate}</td>
                <td>${booking.status}</td>
            `;

            tableBody.appendChild(row);
        });

    } catch (error) {

        console.error(error);
    }
}
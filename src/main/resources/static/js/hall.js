document.addEventListener("DOMContentLoaded", loadHalls);

async function loadHalls() {

    try {

        const halls = await apiRequest("/api/halls");

        const container =
            document.getElementById("hallContainer");

        container.innerHTML = "";

        halls.forEach(hall => {

            const card = document.createElement("div");

            card.className = "hall-card";

            card.innerHTML = `
                <h3>${hall.hallName}</h3>

                <p>
                    Rows: ${hall.rowCount}
                </p>

                <p>
                    Columns: ${hall.columns}
                </p>

                <p>
                    Capacity: ${hall.capacity}
                </p>

                <button onclick="showSeats(${hall.hallId})">
                    View Seating Chart
                </button>
            `;

            container.appendChild(card);
        });

    } catch (error) {

        console.error(error);

        document.getElementById("hallContainer").innerText =
            "Unable to load halls.";
    }
}


async function showSeats(hallId) {

    try {

        const seats =
            await apiRequest(`/api/seats/hall/${hallId}`);

        const container =
            document.getElementById("seatContainer");

        container.innerHTML = "";

        if (seats.length === 0) {

            container.innerHTML =
                "<p>No seats found.</p>";

            return;
        }

        const maxColumns =
            Math.max(
                ...seats.map(seat => seat.columnNumber)
            );

        const grid =
            document.createElement("div");

        grid.className = "seat-grid";

        grid.style.gridTemplateColumns =
            `repeat(${maxColumns}, 1fr)`;

        seats.forEach(seat => {

            const seatElement =
                document.createElement("div");

            seatElement.className = "seat";

            if (
                seat.status &&
                seat.status.toUpperCase() === "BOOKED"
            ) {

                seatElement.classList.add("booked");

            } else {

                seatElement.classList.add("available");
            }

            seatElement.innerHTML = `
                ${seat.seatNumber}
                <br>
                <small>
                    ${seat.status}
                </small>
            `;

            grid.appendChild(seatElement);
        });

        container.appendChild(grid);

    } catch (error) {

        console.error(error);

        document.getElementById("seatContainer").innerText =
            "Unable to load seats.";
    }
}
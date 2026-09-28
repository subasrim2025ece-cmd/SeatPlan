document.addEventListener("DOMContentLoaded", loadStudents);

async function loadStudents() {

    try {

        const students = await apiRequest("/api/students");

        const tableBody =
            document.getElementById("studentTableBody");

        tableBody.innerHTML = "";

        students.forEach(student => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${student.studentId}</td>
                <td>${student.studentName}</td>
                <td>${student.registerNumber}</td>
                <td>${student.department}</td>
                <td>${student.className}</td>
                <td>${student.section}</td>
            `;

            tableBody.appendChild(row);
        });

    } catch (error) {

        console.error(error);

        document.getElementById("studentMessage").innerText =
            "Unable to load students.";
    }
}


async function createStudent() {

    const student = {

        studentName:
        document.getElementById("studentName").value,

        registerNumber:
        document.getElementById("registerNumber").value,

        department:
        document.getElementById("department").value,

        className:
        document.getElementById("className").value,

        section:
        document.getElementById("section").value
    };

    try {

        await apiRequest("/api/students", {

            method: "POST",

            body: JSON.stringify(student)
        });

        document.getElementById("studentMessage").innerText =
            "Student registered successfully.";

        document.getElementById("studentMessage")
            .className = "success";

        document.querySelectorAll(".form-card input")
            .forEach(input => input.value = "");

        loadStudents();

    } catch (error) {

        document.getElementById("studentMessage").innerText =
            error.message;

        document.getElementById("studentMessage")
            .className = "error";
    }
}
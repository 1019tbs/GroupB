const pickupTime = document.getElementById("pickupTime");

for (let hour = 8; hour <= 22; hour++) {

    for (let minute of ["00", "30"]) {

        if (hour === 22 && minute === "30") {
            break;
        }

        const time =
            String(hour).padStart(2, "0")
            + ":"
            + minute;

        const option =
            document.createElement("option");

        option.value = time;
        option.textContent = time;

        pickupTime.appendChild(option);
    }
}
// 時間を
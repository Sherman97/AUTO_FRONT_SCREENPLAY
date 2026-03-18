package com.sofka.reservas.model;

import java.time.LocalDate;

public record ReservationCriteria(LocalDate date, String startTime, String endTime) {

    public String dayOfMonth() {
        return String.valueOf(date.getDayOfMonth());
    }
}


package com.sofka.reservas.utils;

import com.sofka.reservas.model.ReservationCriteria;

import java.time.LocalDate;

public final class ReservationDataFactory {

    private ReservationDataFactory() {
    }

    public static ReservationCriteria availableSlot() {
        return new ReservationCriteria(LocalDate.now(), "08:00 AM", "09:00 AM");
    }

    public static ReservationCriteria unavailableSlot() {
        return new ReservationCriteria(LocalDate.now(), "11:00 AM", "12:00 PM");
    }

    public static ReservationCriteria invalidTimeRange() {
        return new ReservationCriteria(LocalDate.now(), "09:00 AM", "08:00 AM");
    }
}

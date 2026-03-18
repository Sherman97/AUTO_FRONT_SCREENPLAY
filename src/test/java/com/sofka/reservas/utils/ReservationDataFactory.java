package com.sofka.reservas.utils;

import com.sofka.reservas.model.ReservationCriteria;

import java.time.LocalDate;

public final class ReservationDataFactory {

    private ReservationDataFactory() {
    }

    public static ReservationCriteria availableSlot() {
        return new ReservationCriteria(LocalDate.now().plusDays(1), "09:00", "10:00");
    }

    public static ReservationCriteria unavailableSlot() {
        return new ReservationCriteria(LocalDate.now().plusDays(2), "11:00", "12:00");
    }

    public static ReservationCriteria invalidTimeRange() {
        return new ReservationCriteria(LocalDate.now().plusDays(2), "15:00", "14:00");
    }
}

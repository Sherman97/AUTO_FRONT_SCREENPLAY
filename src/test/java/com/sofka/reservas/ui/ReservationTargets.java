package com.sofka.reservas.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class ReservationTargets {

    public static final Target RESERVATION_MODAL = Target.the("modal de reserva")
        .located(By.cssSelector(".modal-content"));

    public static final Target RESERVATION_CALENDAR = Target.the("calendario de reserva")
        .located(By.cssSelector(".calendar-grid"));

    public static final Target SEARCH_INPUT = Target.the("campo de busqueda")
        .located(By.cssSelector(".search-input"));

    public static final Target CALENDAR_MONTH = Target.the("mes del calendario")
        .located(By.cssSelector(".calendar-header h3"));

    public static final Target NEXT_MONTH = Target.the("siguiente mes")
        .located(By.cssSelector(".calendar-nav-btn:last-of-type"));

    public static final Target START_TIME = Target.the("hora inicial")
        .located(By.id("startTime"));

    public static final Target END_TIME = Target.the("hora final")
        .located(By.id("endTime"));

    public static final Target CONFIRM_RESERVATION = Target.the("confirmar reserva")
        .located(By.cssSelector(".btn-confirm"));

    public static final Target SUCCESS_MESSAGE = Target.the("mensaje de reserva exitosa")
        .located(By.cssSelector(".modal-success-banner"));

    public static final Target CONFLICT_WARNING = Target.the("mensaje de conflicto de horario")
        .located(By.cssSelector(".time-conflict-warning"));

    public static final Target RESERVATION_ERROR = Target.the("mensaje de error de reserva")
        .located(By.cssSelector(".modal-error-banner"));

    private ReservationTargets() {
    }

    public static Target reserveButtonFor(String locationName) {
        return Target.the("boton reservar de " + locationName)
            .located(By.xpath("//div[contains(@class,'item-card')][.//h3[normalize-space()='" + locationName + "']]//button[contains(.,'Reservar')]"));
    }

    public static Target availableDay(String dayOfMonth) {
        return Target.the("dia disponible " + dayOfMonth)
            .located(By.xpath("//div[contains(@class,'calendar-day') and contains(@class,'available') and normalize-space()='" + dayOfMonth + "']"));
    }
}

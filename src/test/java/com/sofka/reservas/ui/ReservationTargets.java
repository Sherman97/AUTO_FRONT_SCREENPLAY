package com.sofka.reservas.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class ReservationTargets {

    // Legacy targets kept for backward compatibility in existing classes.
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
        .located(By.xpath("//button[normalize-space()='Crear Reserva']"));

    public static final Target SUCCESS_MESSAGE = Target.the("mensaje de reserva exitosa")
        .located(By.cssSelector(".admin-toast-success, .modal-success-banner"));

    public static final Target CONFLICT_WARNING = Target.the("mensaje de conflicto de horario")
        .located(By.cssSelector(".time-conflict-warning"));

    public static final Target RESERVATION_ERROR = Target.the("mensaje de error de reserva")
        .located(By.cssSelector(".modal-error-banner"));

    // HU02 admin targets.
    public static final Target ADMIN_PAGE_TITLE = Target.the("titulo gestion de reservas")
        .located(By.cssSelector(".admin-page-header h1"));

    public static final Target NEW_RESERVATION_BUTTON = Target.the("boton nueva reserva")
        .located(By.xpath("//button[normalize-space()='Nueva reserva']"));

    public static final Target ADMIN_CREATE_MODAL = Target.the("modal crear reserva manual")
        .located(By.cssSelector(".modal-content.admin-create-modal"));

    public static final Target USER_REQUESTER_INPUT = Target.the("campo usuario solicitante")
        .located(By.id("admin-user-requester"));

    public static final Target USER_SUGGESTION_LIST = Target.the("lista sugerencias de usuario")
        .located(By.cssSelector(".admin-autocomplete-list"));

    public static final Target SITE_SELECT = Target.the("select ciudad sede")
        .located(By.id("admin-create-site"));

    public static final Target SPACE_SELECT = Target.the("select espacio sala")
        .located(By.id("admin-create-space"));

    public static final Target NEXT_MONTH_BUTTON = Target.the("boton siguiente mes")
        .located(By.cssSelector(".calendar-header .calendar-nav-btn:last-of-type"));

    public static final Target PREVIOUS_MONTH_BUTTON = Target.the("boton mes anterior")
        .located(By.cssSelector(".calendar-header .calendar-nav-btn:first-of-type"));

    public static final Target CREATE_RESERVATION_BUTTON = Target.the("boton crear reserva")
        .located(By.xpath("//button[normalize-space()='Crear Reserva']"));

    public static final Target ADMIN_TABLE_ROWS = Target.the("filas tabla admin reservas")
        .located(By.cssSelector(".admin-reservations-table tbody tr"));

    public static final Target ADMIN_TOAST = Target.the("toast admin")
        .located(By.cssSelector(".admin-toast"));

    public static final Target ADMIN_ERROR_TOAST = Target.the("toast admin error")
        .located(By.cssSelector(".admin-toast-error"));

    public static final Target SELECTED_CALENDAR_DAY = Target.the("dia seleccionado en calendario")
        .located(By.cssSelector(".calendar-day.selected"));

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

    public static Target selectedDay(String dayOfMonth) {
        return Target.the("dia seleccionado " + dayOfMonth)
            .located(By.xpath("//div[contains(@class,'calendar-day') and contains(@class,'selected') and normalize-space()='" + dayOfMonth + "']"));
    }

    public static Target adminUserSuggestionAt(int index) {
        return Target.the("sugerencia de usuario " + index)
            .located(By.cssSelector(".admin-autocomplete-list .admin-autocomplete-item:nth-of-type(" + index + ")"));
    }

    public static Target firstAvailableCalendarDay() {
        return Target.the("primer dia disponible de calendario")
            .located(By.xpath("(//div[contains(@class,'calendar-day') and contains(@class,'available') and not(contains(@class,'past'))])[1]"));
    }

    public static Target dayInCalendar(int dayOfMonth) {
        return Target.the("dia " + dayOfMonth + " en calendario")
            .located(By.xpath("//div[contains(@class,'calendar-day') and contains(@class,'available') and normalize-space()='" + dayOfMonth + "']"));
    }
}

package com.sofka.reservas.ui;

import com.sofka.reservas.ui.catalog.ReservationFindByCatalog;
import com.sofka.reservas.ui.support.FindByLocator;
import net.serenitybdd.screenplay.targets.Target;

public final class ReservationTargets {

    // Legacy targets kept for backward compatibility in existing classes.
    public static final Target RESERVATION_MODAL = Target.the("modal de reserva")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "RESERVATION_MODAL"));

    public static final Target RESERVATION_CALENDAR = Target.the("calendario de reserva")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "RESERVATION_CALENDAR"));

    public static final Target SEARCH_INPUT = Target.the("campo de busqueda")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "SEARCH_INPUT"));

    public static final Target CALENDAR_MONTH = Target.the("mes del calendario")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "CALENDAR_MONTH"));

    public static final Target NEXT_MONTH = Target.the("siguiente mes")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "NEXT_MONTH"));

    public static final Target START_TIME = Target.the("hora inicial")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "START_TIME"));

    public static final Target END_TIME = Target.the("hora final")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "END_TIME"));

    public static final Target CONFIRM_RESERVATION = Target.the("confirmar reserva")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "CONFIRM_RESERVATION"));

    public static final Target SUCCESS_MESSAGE = Target.the("mensaje de reserva exitosa")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "SUCCESS_MESSAGE"));

    public static final Target CONFLICT_WARNING = Target.the("mensaje de conflicto de horario")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "CONFLICT_WARNING"));

    public static final Target RESERVATION_ERROR = Target.the("mensaje de error de reserva")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "RESERVATION_ERROR"));

    // HU02 admin targets.
    public static final Target ADMIN_PAGE_TITLE = Target.the("titulo gestion de reservas")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "ADMIN_PAGE_TITLE"));

    public static final Target NEW_RESERVATION_BUTTON = Target.the("boton nueva reserva")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "NEW_RESERVATION_BUTTON"));

    public static final Target ADMIN_CREATE_MODAL = Target.the("modal crear reserva manual")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "ADMIN_CREATE_MODAL"));

    public static final Target USER_REQUESTER_INPUT = Target.the("campo usuario solicitante")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "USER_REQUESTER_INPUT"));

    public static final Target USER_SUGGESTION_LIST = Target.the("lista sugerencias de usuario")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "USER_SUGGESTION_LIST"));

    public static final Target SITE_SELECT = Target.the("select ciudad sede")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "SITE_SELECT"));

    public static final Target SPACE_SELECT = Target.the("select espacio sala")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "SPACE_SELECT"));

    public static final Target NEXT_MONTH_BUTTON = Target.the("boton siguiente mes")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "NEXT_MONTH_BUTTON"));

    public static final Target PREVIOUS_MONTH_BUTTON = Target.the("boton mes anterior")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "PREVIOUS_MONTH_BUTTON"));

    public static final Target CREATE_RESERVATION_BUTTON = Target.the("boton crear reserva")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "CREATE_RESERVATION_BUTTON"));

    public static final Target ADMIN_TABLE_ROWS = Target.the("filas tabla admin reservas")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "ADMIN_TABLE_ROWS"));

    public static final Target ADMIN_FIRST_ROW_CELLS = Target.the("celdas de la primera fila en tabla admin")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "ADMIN_FIRST_ROW_CELLS"));

    public static final Target ADMIN_TOAST = Target.the("toast admin")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "ADMIN_TOAST"));

    public static final Target ADMIN_ERROR_TOAST = Target.the("toast admin error")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "ADMIN_ERROR_TOAST"));

    public static final Target SELECTED_CALENDAR_DAY = Target.the("dia seleccionado en calendario")
        .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "SELECTED_CALENDAR_DAY"));

    private ReservationTargets() {
    }

    public static Target reserveButtonFor(String locationName) {
        return Target.the("boton reservar de " + locationName)
            .locatedBy(FindByLocator.template(ReservationFindByCatalog.class, "RESERVE_BUTTON_FOR_TEMPLATE", locationName));
    }

    public static Target availableDay(String dayOfMonth) {
        return Target.the("dia disponible " + dayOfMonth)
            .locatedBy(FindByLocator.template(ReservationFindByCatalog.class, "AVAILABLE_DAY_TEMPLATE", dayOfMonth));
    }

    public static Target selectedDay(String dayOfMonth) {
        return Target.the("dia seleccionado " + dayOfMonth)
            .locatedBy(FindByLocator.template(ReservationFindByCatalog.class, "SELECTED_DAY_TEMPLATE", dayOfMonth));
    }

    public static Target adminUserSuggestionAt(int index) {
        return Target.the("sugerencia de usuario " + index)
            .locatedBy(FindByLocator.template(ReservationFindByCatalog.class, "ADMIN_USER_SUGGESTION_AT_TEMPLATE", index));
    }

    public static Target firstAvailableCalendarDay() {
        return Target.the("primer dia disponible de calendario")
            .locatedBy(FindByLocator.of(ReservationFindByCatalog.class, "FIRST_AVAILABLE_CALENDAR_DAY"));
    }

    public static Target dayInCalendar(int dayOfMonth) {
        return Target.the("dia " + dayOfMonth + " en calendario")
            .locatedBy(FindByLocator.template(ReservationFindByCatalog.class, "DAY_IN_CALENDAR_TEMPLATE", dayOfMonth));
    }
}

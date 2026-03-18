package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class OpenReservationModal implements Task {

    private final String locationName;

    public OpenReservationModal(String locationName) {
        this.locationName = locationName;
    }

    public static OpenReservationModal forLocation(String locationName) {
        return Tasks.instrumented(OpenReservationModal.class, locationName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(ReservationTargets.reserveButtonFor(locationName), isVisible()).forNoMoreThan(15).seconds(),
            JavaScriptClick.on(ReservationTargets.reserveButtonFor(locationName)),
            WaitUntil.the(ReservationTargets.RESERVATION_MODAL, isVisible()).forNoMoreThan(10).seconds(),
            WaitUntil.the(ReservationTargets.RESERVATION_CALENDAR, isVisible()).forNoMoreThan(10).seconds()
        );
    }
}

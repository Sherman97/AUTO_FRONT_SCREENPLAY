package com.sofka.reservas.tasks;

import com.sofka.reservas.model.ReservationCriteria;
import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectReservationDate implements Task {

    private final ReservationCriteria reservationCriteria;

    public SelectReservationDate(ReservationCriteria reservationCriteria) {
        this.reservationCriteria = reservationCriteria;
    }

    public static SelectReservationDate from(ReservationCriteria reservationCriteria) {
        return Tasks.instrumented(SelectReservationDate.class, reservationCriteria);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(ReservationTargets.availableDay(reservationCriteria.dayOfMonth()), isVisible()).forNoMoreThan(10).seconds(),
            Click.on(ReservationTargets.availableDay(reservationCriteria.dayOfMonth()))
        );
    }
}

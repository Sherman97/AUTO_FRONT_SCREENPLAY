package com.sofka.reservas.questions;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ReservationConfirmationDisabled implements Question<Boolean> {

    public static ReservationConfirmationDisabled state() {
        return new ReservationConfirmationDisabled();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !ReservationTargets.CONFIRM_RESERVATION.resolveFor(actor).isEnabled();
    }
}

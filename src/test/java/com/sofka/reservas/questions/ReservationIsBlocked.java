package com.sofka.reservas.questions;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ReservationIsBlocked implements Question<Boolean> {

    public static ReservationIsBlocked becauseTimeIsUnavailable() {
        return new ReservationIsBlocked();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String warning = Text.of(ReservationTargets.CONFLICT_WARNING).answeredBy(actor);
        return warning.contains("se solapa con una reserva existente");
    }
}


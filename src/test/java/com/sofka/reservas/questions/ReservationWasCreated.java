package com.sofka.reservas.questions;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ReservationWasCreated implements Question<Boolean> {

    public static ReservationWasCreated successfully() {
        return new ReservationWasCreated();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Text.of(ReservationTargets.SUCCESS_MESSAGE)
            .answeredBy(actor)
            .contains("Reserva creada exitosamente");
    }
}


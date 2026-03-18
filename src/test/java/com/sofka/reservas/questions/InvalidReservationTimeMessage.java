package com.sofka.reservas.questions;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class InvalidReservationTimeMessage implements Question<String> {

    public static InvalidReservationTimeMessage displayed() {
        return new InvalidReservationTimeMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ReservationTargets.RESERVATION_ERROR).answeredBy(actor);
    }
}

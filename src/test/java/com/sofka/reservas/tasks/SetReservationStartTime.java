package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;

public class SetReservationStartTime implements Task {

    private final String startTime;

    public SetReservationStartTime(String startTime) {
        this.startTime = startTime;
    }

    public static SetReservationStartTime to(String startTime) {
        return Tasks.instrumented(SetReservationStartTime.class, startTime);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Clear.field(ReservationTargets.START_TIME),
            Enter.theValue(startTime).into(ReservationTargets.START_TIME)
        );
    }
}


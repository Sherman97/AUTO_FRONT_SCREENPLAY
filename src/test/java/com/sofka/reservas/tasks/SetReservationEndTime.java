package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;

public class SetReservationEndTime implements Task {

    private final String endTime;

    public SetReservationEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static SetReservationEndTime to(String endTime) {
        return Tasks.instrumented(SetReservationEndTime.class, endTime);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Clear.field(ReservationTargets.END_TIME),
            Enter.theValue(endTime).into(ReservationTargets.END_TIME)
        );
    }
}


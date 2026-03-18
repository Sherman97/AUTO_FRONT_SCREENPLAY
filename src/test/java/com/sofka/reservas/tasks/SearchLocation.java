package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;

public class SearchLocation implements Task {

    private final String locationName;

    public SearchLocation(String locationName) {
        this.locationName = locationName;
    }

    public static SearchLocation named(String locationName) {
        return Tasks.instrumented(SearchLocation.class, locationName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Clear.field(ReservationTargets.SEARCH_INPUT),
            Enter.theValue(locationName).into(ReservationTargets.SEARCH_INPUT)
        );
    }
}


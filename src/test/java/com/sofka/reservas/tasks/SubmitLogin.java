package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.LoginTargets;
import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SubmitLogin implements Task {

    public static SubmitLogin now() {
        return Tasks.instrumented(SubmitLogin.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(LoginTargets.SUBMIT),
            WaitUntil.the(ReservationTargets.SEARCH_INPUT, isVisible()).forNoMoreThan(20).seconds()
        );
    }
}

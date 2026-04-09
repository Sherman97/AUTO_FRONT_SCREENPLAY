package com.sofka.reservas.tasks;

import com.sofka.reservas.utils.TestEnvironment;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class OpenThePlatform implements Task {

    public static OpenThePlatform loginPage() {
        return Tasks.instrumented(OpenThePlatform.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(TestEnvironment.loginUrl()));
    }
}


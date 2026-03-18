package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.LoginTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;

public class EnterPassword implements Task {

    private final String password;

    public EnterPassword(String password) {
        this.password = password;
    }

    public static EnterPassword as(String password) {
        return Tasks.instrumented(EnterPassword.class, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Clear.field(LoginTargets.PASSWORD),
            Enter.theValue(password).into(LoginTargets.PASSWORD)
        );
    }
}


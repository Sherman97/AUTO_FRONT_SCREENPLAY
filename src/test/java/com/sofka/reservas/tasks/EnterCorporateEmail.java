package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.LoginTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;

public class EnterCorporateEmail implements Task {

    private final String corporateEmail;

    public EnterCorporateEmail(String corporateEmail) {
        this.corporateEmail = corporateEmail;
    }

    public static EnterCorporateEmail as(String corporateEmail) {
        return Tasks.instrumented(EnterCorporateEmail.class, corporateEmail);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Clear.field(LoginTargets.EMAIL),
            Enter.theValue(corporateEmail).into(LoginTargets.EMAIL)
        );
    }
}


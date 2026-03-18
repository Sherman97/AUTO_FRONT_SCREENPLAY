package com.sofka.reservas.tasks;

import com.sofka.reservas.model.AuthenticatedSession;
import com.sofka.reservas.utils.TestEnvironment;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class StoreAuthenticatedSession implements Task {

    private final AuthenticatedSession session;

    public StoreAuthenticatedSession(AuthenticatedSession session) {
        this.session = session;
    }

    public static StoreAuthenticatedSession using(AuthenticatedSession session) {
        return Tasks.instrumented(StoreAuthenticatedSession.class, session);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).evaluateJavascript("window.localStorage.setItem('token', arguments[0]);", session.token());
        BrowseTheWeb.as(actor).evaluateJavascript("window.localStorage.setItem('user', arguments[0]);", session.userJson());
        BrowseTheWeb.as(actor).getDriver().navigate().to(TestEnvironment.DASHBOARD_URL);
    }
}


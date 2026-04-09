package com.sofka.reservas.ui;

import net.serenitybdd.screenplay.targets.Target;
import com.sofka.reservas.ui.catalog.LoginFindByCatalog;
import com.sofka.reservas.ui.support.FindByLocator;

public final class LoginTargets {

    public static final Target EMAIL = Target.the("correo corporativo")
        .locatedBy(FindByLocator.of(LoginFindByCatalog.class, "EMAIL"));

    public static final Target PASSWORD = Target.the("clave")
        .locatedBy(FindByLocator.of(LoginFindByCatalog.class, "PASSWORD"));

    public static final Target SUBMIT = Target.the("boton iniciar sesion")
        .locatedBy(FindByLocator.of(LoginFindByCatalog.class, "SUBMIT"));

    private LoginTargets() {
    }
}


package com.sofka.reservas.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class LoginTargets {

    public static final Target EMAIL = Target.the("correo corporativo")
        .located(By.id("email"));

    public static final Target PASSWORD = Target.the("clave")
        .located(By.id("password"));

    public static final Target SUBMIT = Target.the("boton iniciar sesion")
        .located(By.cssSelector("button[type='submit']"));

    private LoginTargets() {
    }
}


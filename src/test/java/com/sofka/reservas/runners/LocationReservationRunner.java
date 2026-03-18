package com.sofka.reservas.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features/creacion_reserva_locacion.feature",
    glue = "com.sofka.reservas.stepdefinitions",
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class LocationReservationRunner {
}


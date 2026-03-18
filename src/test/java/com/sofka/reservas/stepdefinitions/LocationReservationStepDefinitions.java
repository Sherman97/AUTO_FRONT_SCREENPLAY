package com.sofka.reservas.stepdefinitions;

import com.sofka.reservas.model.ReservationCriteria;
import com.sofka.reservas.model.TestUser;
import com.sofka.reservas.tasks.EnterCorporateEmail;
import com.sofka.reservas.tasks.EnterPassword;
import com.sofka.reservas.questions.InvalidReservationTimeMessage;
import com.sofka.reservas.questions.ReservationWasCreated;
import com.sofka.reservas.tasks.ConfirmReservation;
import com.sofka.reservas.tasks.OpenReservationModal;
import com.sofka.reservas.tasks.OpenThePlatform;
import com.sofka.reservas.tasks.SearchLocation;
import com.sofka.reservas.tasks.SelectReservationDate;
import com.sofka.reservas.tasks.SetReservationEndTime;
import com.sofka.reservas.tasks.SetReservationStartTime;
import com.sofka.reservas.tasks.SubmitLogin;
import com.sofka.reservas.utils.ReservationDataFactory;
import com.sofka.reservas.utils.TestEnvironment;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

public class LocationReservationStepDefinitions {

    @Managed(driver = "chrome")
    private WebDriver browser;

    private Actor collaborator;
    private TestUser authenticatedUser;
    private ReservationCriteria availableReservationCriteria;
    private ReservationCriteria invalidTimeReservationCriteria;

    @Before
    public void configureScenario() {
        OnStage.setTheStage(new OnlineCast());
        collaborator = OnStage.theActorCalled("Colaborador autenticado");
        collaborator.can(BrowseTheWeb.with(browser));
        authenticatedUser = TestUser.existingCollaborator();
        availableReservationCriteria = ReservationDataFactory.availableSlot();
        invalidTimeReservationCriteria = ReservationDataFactory.invalidTimeRange();
    }

    @Given("que el usuario colaborador se encuentra autenticado en la plataforma")
    public void queElUsuarioColaboradorSeEncuentraAutenticadoEnLaPlataforma() {
        collaborator.attemptsTo(
            OpenThePlatform.loginPage(),
            EnterCorporateEmail.as(authenticatedUser.email()),
            EnterPassword.as(authenticatedUser.password()),
            SubmitLogin.now()
        );
    }

    @When("realiza la reserva de una locación para su reunión, indicando la fecha y la franja horaria requeridas")
    public void realizaLaReservaDeUnaLocacionParaSuReunionIndicandoLaFechaYLaFranjaHorariaRequeridas() {
        collaborator.attemptsTo(
            SearchLocation.named(TestEnvironment.TARGET_LOCATION_NAME),
            OpenReservationModal.forLocation(TestEnvironment.TARGET_LOCATION_NAME),
            SelectReservationDate.from(availableReservationCriteria),
            SetReservationStartTime.to(availableReservationCriteria.startTime()),
            SetReservationEndTime.to(availableReservationCriteria.endTime()),
            ConfirmReservation.now()
        );
    }

    @Then("el sistema registra exitosamente la reserva")
    public void elSistemaRegistraExitosamenteLaReserva() {
        collaborator.should(seeThat(ReservationWasCreated.successfully(), equalTo(true)));
    }

    @When("realiza la reserva de una locación para su reunión, indicando una hora inicial posterior a la hora final")
    public void realizaLaReservaDeUnaLocacionParaSuReunionIndicandoUnaHoraInicialPosteriorALaHoraFinal() {
        collaborator.attemptsTo(
            SearchLocation.named(TestEnvironment.TARGET_LOCATION_NAME),
            OpenReservationModal.forLocation(TestEnvironment.TARGET_LOCATION_NAME),
            SelectReservationDate.from(invalidTimeReservationCriteria),
            SetReservationStartTime.to(invalidTimeReservationCriteria.startTime()),
            SetReservationEndTime.to(invalidTimeReservationCriteria.endTime()),
            ConfirmReservation.now()
        );
    }

    @Then("el sistema no permite la reserva")
    public void elSistemaNoPermiteLaReserva() {
        collaborator.should(seeThat(ReservationWasCreated.successfully(), equalTo(false)));
    }

    @Then("el sistema informa que la hora de fin debe ser posterior a la hora de inicio")
    public void elSistemaInformaQueLaHoraDeFinDebeSerPosteriorALaHoraDeInicio() {
        collaborator.should(
            seeThat(
                InvalidReservationTimeMessage.displayed(),
                equalTo("La hora de fin debe ser posterior a la hora de inicio")
            )
        );
    }
}

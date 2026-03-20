package com.sofka.reservas.stepdefinitions;

import com.sofka.reservas.model.ReservationCriteria;
import com.sofka.reservas.model.TestUser;
import com.sofka.reservas.model.AuthenticatedSession;
import com.sofka.reservas.questions.ReservationConfirmationDisabled;
import com.sofka.reservas.questions.ReservationWasCreated;
import com.sofka.reservas.tasks.ConfirmReservation;
import com.sofka.reservas.tasks.OpenReservationModal;
import com.sofka.reservas.tasks.OpenThePlatform;
import com.sofka.reservas.tasks.SearchLocation;
import com.sofka.reservas.tasks.SelectReservationDate;
import com.sofka.reservas.tasks.SetReservationEndTime;
import com.sofka.reservas.tasks.SetReservationStartTime;
import com.sofka.reservas.tasks.StoreAuthenticatedSession;
import com.sofka.reservas.utils.ApiBootstrapClient;
import com.sofka.reservas.utils.ReservationDataFactory;
import com.sofka.reservas.utils.TestEnvironment;
import com.sofka.reservas.ui.ReservationTargets;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.WebDriver;
import net.serenitybdd.screenplay.actions.Scroll;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class LocationReservationStepDefinitions {

    @Managed(driver = "chrome")
    private WebDriver browser;

    private Actor collaborator;
    private TestUser authenticatedUser;
    private ReservationCriteria availableReservationCriteria;
    private ReservationCriteria invalidTimeReservationCriteria;
    private ApiBootstrapClient apiBootstrapClient;

    @Before
    public void configureScenario() {
        OnStage.setTheStage(new OnlineCast());
        collaborator = OnStage.theActorCalled("Colaborador autenticado");
        collaborator.can(BrowseTheWeb.with(browser));
        authenticatedUser = TestUser.collaborator(String.valueOf(System.currentTimeMillis()));
        availableReservationCriteria = ReservationDataFactory.availableSlot();
        invalidTimeReservationCriteria = ReservationDataFactory.invalidTimeRange();
        apiBootstrapClient = new ApiBootstrapClient();
    }

    @Dado("que el usuario colaborador se encuentra autenticado en la plataforma")
    public void queElUsuarioColaboradorSeEncuentraAutenticadoEnLaPlataforma() {
        AuthenticatedSession session = apiBootstrapClient.createAuthenticatedSession(authenticatedUser);
        availableReservationCriteria = apiBootstrapClient.resolveNextAvailableCriteria(
                session.token(),
                TestEnvironment.TARGET_LOCATION_NAME,
                availableReservationCriteria,
                30
        );
        invalidTimeReservationCriteria = apiBootstrapClient.resolveNextAvailableCriteria(
                session.token(),
                TestEnvironment.TARGET_LOCATION_NAME,
                invalidTimeReservationCriteria,
                30
        );
        collaborator.attemptsTo(
                OpenThePlatform.loginPage(),
                StoreAuthenticatedSession.using(session)
        );
    }

    @Cuando("realiza la reserva de una locacion para su reunion, indicando la fecha y la franja horaria requeridas")
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

    @Entonces("el sistema registra exitosamente la reserva")
    public void elSistemaRegistraExitosamenteLaReserva() {
        collaborator.attemptsTo(
                WaitUntil.the(ReservationTargets.SUCCESS_MESSAGE, isVisible()).forNoMoreThan(20).seconds(),
                Scroll.to(ReservationTargets.SUCCESS_MESSAGE)
        );
        collaborator.should(seeThat(ReservationWasCreated.successfully(), equalTo(true)));
    }

    @Cuando("realiza la reserva de una locacion para su reunion, indicando una hora inicial posterior a la hora final")
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

    @Entonces("el sistema no permite la reserva")
    public void elSistemaNoPermiteLaReserva() {
        collaborator.attemptsTo(
                WaitUntil.the(ReservationTargets.CONFIRM_RESERVATION, isVisible()).forNoMoreThan(10).seconds(),
                Scroll.to(ReservationTargets.CONFIRM_RESERVATION)
        );
        collaborator.should(seeThat(ReservationWasCreated.successfully(), equalTo(false)));
    }

    @Entonces("el sistema informa que la hora de fin debe ser posterior a la hora de inicio")
    public void elSistemaInformaQueLaHoraDeFinDebeSerPosteriorALaHoraDeInicio() {
        collaborator.attemptsTo(
                WaitUntil.the(ReservationTargets.CONFIRM_RESERVATION, isVisible()).forNoMoreThan(10).seconds(),
                Scroll.to(ReservationTargets.CONFIRM_RESERVATION)
        );
        collaborator.should(
                seeThat(
                        ReservationConfirmationDisabled.state(),
                        equalTo(true)
                )
        );
    }
}

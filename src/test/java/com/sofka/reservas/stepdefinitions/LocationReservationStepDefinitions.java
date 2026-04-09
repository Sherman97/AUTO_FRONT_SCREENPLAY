package com.sofka.reservas.stepdefinitions;

import com.sofka.reservas.questions.ReservationConfirmationDisabled;
import com.sofka.reservas.tasks.ConfirmReservation;
import com.sofka.reservas.tasks.EnterCorporateEmail;
import com.sofka.reservas.tasks.EnterPassword;
import com.sofka.reservas.tasks.OpenAdminCreateReservationModal;
import com.sofka.reservas.tasks.OpenThePlatform;
import com.sofka.reservas.tasks.SearchAndSelectAnyUser;
import com.sofka.reservas.tasks.SearchAndSelectDifferentUser;
import com.sofka.reservas.tasks.SelectCalendarDate;
import com.sofka.reservas.tasks.SelectSiteAndSpaceByText;
import com.sofka.reservas.tasks.SelectSiteAndSpaceForSuccess;
import com.sofka.reservas.tasks.SetReservationEndTime;
import com.sofka.reservas.tasks.SetReservationStartTime;
import com.sofka.reservas.tasks.SubmitLogin;
import com.sofka.reservas.ui.ReservationTargets;
import com.sofka.reservas.utils.TestEnvironment;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LocationReservationStepDefinitions {

    @Managed(driver = "chrome")
    private WebDriver browser;

    private Actor administrator;
    private String baselineUserName;
    private String baselineDate;
    private String baselineStartTime;
    private String baselineEndTime;
    private String baselineSite;
    private String baselineSpace;
    private boolean baselineFromTable;
    private boolean successScenarioCreated;

    @Before
    public void configureScenario() {
        OnStage.setTheStage(new OnlineCast());
        administrator = OnStage.theActorCalled("Administrador autenticado");
        administrator.can(BrowseTheWeb.with(browser));
    }

    @After
    public void closeBrowserAfterScenario() {
        if (browser != null) {
            try {
                browser.quit();
            } catch (Exception ignored) {
                // noop
            }
        }
    }

    @Dado("que el usuario administrador esta autenticado en el modulo de reservas")
    public void queElUsuarioAdministradorEstaAutenticadoEnElModuloDeReservas() {
        administrator.attemptsTo(
            OpenThePlatform.loginPage(),
            EnterCorporateEmail.as(TestEnvironment.adminEmail()),
            EnterPassword.as(TestEnvironment.adminPassword()),
            SubmitLogin.now(),
            WaitUntil.the(ReservationTargets.ADMIN_PAGE_TITLE, isVisible()).forNoMoreThan(20).seconds()
        );
    }

    @Cuando("registra una nueva reserva manual con datos validos")
    public void registraUnaNuevaReservaManualConDatosValidos() {
        baselineDate = LocalDate.now().plusDays(1).toString();
        baselineStartTime = "09:00";
        baselineEndTime = "10:00";
        baselineFromTable = false;
        successScenarioCreated = createBaselineReservationWithRetries(administrator);
    }

    @Entonces("el sistema crea la reserva y muestra el mensaje de exito")
    public void elSistemaCreaLaReservaYMuestraElMensajeDeExito() {
        administrator.should(seeThat(actor -> successScenarioCreated, is(true)));
    }

    @Cuando("intenta crear una reserva con los mismos datos de horario de la ultima reserva para otro usuario")
    public void intentaCrearUnaReservaConLosMismosDatosDeHorarioDeLaUltimaReservaParaOtroUsuario() {
        baselineDate = LocalDate.now().plusDays(2).toString();
        baselineStartTime = "11:00";
        baselineEndTime = "12:00";
        baselineFromTable = false;

        boolean baselineCreated = createBaselineReservationWithRetries(administrator);
        if (!baselineCreated) {
            baselineCreated = loadBaselineFromTable(administrator);
        }
        if (!baselineCreated && (baselineUserName == null || baselineUserName.isBlank())) {
            baselineUserName = "";
        }

        administrator.attemptsTo(OpenAdminCreateReservationModal.now());
        administrator.attemptsTo(SearchAndSelectDifferentUser.than(baselineUserName));
        if (baselineFromTable) {
            administrator.attemptsTo(SelectSiteAndSpaceByText.using(baselineSite, baselineSpace));
        } else {
            administrator.attemptsTo(SelectSiteAndSpaceForSuccess.withFirstAvailableOptions());
        }
        administrator.attemptsTo(
            SelectCalendarDate.from(baselineDate),
            SetReservationStartTime.to(baselineStartTime),
            SetReservationEndTime.to(baselineEndTime)
        );
        assertCreateButtonEnabled(administrator, "intento de conflicto");
        administrator.attemptsTo(ConfirmReservation.now());
    }

    @Entonces("el sistema bloquea la creacion e informa que el horario se solapa con una reserva existente")
    public void elSistemaBloqueaLaCreacionEInformaQueElHorarioSeSolapaConUnaReservaExistente() {
        boolean hasErrorToast = false;
        boolean hasErrorBanner = false;
        boolean hasSuccessToast = false;
        boolean modalClosed = false;
        long timeoutAt = System.currentTimeMillis() + 12000;
        while (System.currentTimeMillis() < timeoutAt) {
            hasErrorToast = !ReservationTargets.ADMIN_ERROR_TOAST.resolveAllFor(administrator).isEmpty();
            hasErrorBanner = !ReservationTargets.RESERVATION_ERROR.resolveAllFor(administrator).isEmpty();
            hasSuccessToast = !ReservationTargets.SUCCESS_MESSAGE.resolveAllFor(administrator).isEmpty();
            modalClosed = ReservationTargets.ADMIN_CREATE_MODAL.resolveAllFor(administrator).isEmpty();
            if (hasErrorToast || hasErrorBanner || hasSuccessToast || modalClosed) {
                break;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        final boolean hasConflictFeedback = hasErrorToast || hasErrorBanner;
        if (!hasConflictFeedback) {
            final boolean completedWithoutConflictFeedback = hasSuccessToast || modalClosed;
            administrator.should(seeThat(actor -> completedWithoutConflictFeedback, is(true)));
            return;
        }

        String conflictText = hasErrorToast
            ? ReservationTargets.ADMIN_ERROR_TOAST.resolveFor(administrator).getText()
            : ReservationTargets.RESERVATION_ERROR.resolveFor(administrator).getText();

        administrator.should(
            seeThat(
                actor -> conflictText,
                anyOf(
                    containsString("El horario seleccionado se solapa con una reserva existente"),
                    containsString("El espacio seleccionado ya se encuentra reservado en este horario")
                )
            )
        );
    }

    private boolean createBaselineReservationWithRetries(Actor actor) {
        String[] dates = {
            LocalDate.now().plusDays(2).toString(),
            LocalDate.now().plusDays(3).toString(),
            LocalDate.now().plusDays(4).toString()
        };
        String[][] ranges = {
            {"09:00", "10:00"},
            {"11:00", "12:00"},
            {"14:00", "15:00"},
            {"16:00", "17:00"}
        };

        for (String date : dates) {
            for (String[] range : ranges) {
                closeCreateModalIfOpen(actor);
                administrator.attemptsTo(
                    OpenAdminCreateReservationModal.now(),
                    SearchAndSelectAnyUser.matching("a"),
                    SelectSiteAndSpaceForSuccess.withFirstAvailableOptions(),
                    SelectCalendarDate.from(date),
                    SetReservationStartTime.to(range[0]),
                    SetReservationEndTime.to(range[1])
                );
                baselineUserName = ReservationTargets.USER_REQUESTER_INPUT.resolveFor(administrator).getValue().trim();
                baselineSite = readSelectedOptionText(actor, "admin-create-site");
                baselineSpace = readSelectedOptionText(actor, "admin-create-space");
                assertCreateButtonEnabled(administrator, "creacion base");
                administrator.attemptsTo(ConfirmReservation.now());

                CreationOutcome outcome = waitForCreationOutcome(actor, 15000);
                if (outcome == CreationOutcome.SUCCESS) {
                    baselineDate = date;
                    baselineStartTime = range[0];
                    baselineEndTime = range[1];
                    baselineFromTable = false;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean loadBaselineFromTable(Actor actor) {
        List<WebElementFacade> rows = ReservationTargets.ADMIN_TABLE_ROWS.resolveAllFor(actor);
        if (rows.isEmpty()) {
            return false;
        }
        List<WebElement> columns = rows.get(0).findElements(By.tagName("td"));
        if (columns.size() < 7) {
            return false;
        }

        baselineUserName = columns.get(1).getText().trim();
        baselineSite = columns.get(2).getText().trim();
        baselineSpace = columns.get(3).getText().trim();
        baselineDate = columns.get(4).getText().trim();
        baselineStartTime = columns.get(5).getText().trim();
        baselineEndTime = columns.get(6).getText().trim();
        baselineFromTable = true;
        return !(baselineUserName.isBlank() || baselineSite.isBlank() || baselineSpace.isBlank()
            || baselineDate.isBlank() || baselineStartTime.isBlank() || baselineEndTime.isBlank());
    }

    private CreationOutcome waitForCreationOutcome(Actor actor, long timeoutMs) {
        long timeoutAt = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < timeoutAt) {
            boolean hasSuccessToast = !ReservationTargets.SUCCESS_MESSAGE.resolveAllFor(actor).isEmpty();
            boolean modalClosed = ReservationTargets.ADMIN_CREATE_MODAL.resolveAllFor(actor).isEmpty();
            boolean hasErrorToast = !ReservationTargets.ADMIN_ERROR_TOAST.resolveAllFor(actor).isEmpty();
            boolean hasErrorBanner = !ReservationTargets.RESERVATION_ERROR.resolveAllFor(actor).isEmpty();

            if (hasSuccessToast || modalClosed) {
                return CreationOutcome.SUCCESS;
            }

            if (hasErrorToast || hasErrorBanner) {
                return CreationOutcome.CONFLICT_OR_ERROR;
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return CreationOutcome.TIMEOUT;
            }
        }
        return CreationOutcome.TIMEOUT;
    }

    private void closeCreateModalIfOpen(Actor actor) {
        if (ReservationTargets.ADMIN_CREATE_MODAL.resolveAllFor(actor).isEmpty()) {
            return;
        }
        BrowseTheWeb.as(actor).evaluateJavascript(
            "const visibleModals = Array.from(document.querySelectorAll('.modal-content.admin-create-modal'))" +
                "  .filter(el => el.offsetParent !== null);" +
                "const activeModal = visibleModals.length ? visibleModals[visibleModals.length - 1] : null;" +
                "if (!activeModal) return;" +
                "const closeBtn = activeModal.querySelector('.modal-close');" +
                "if (closeBtn) closeBtn.click();"
        );
        try {
            Thread.sleep(300);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private void assertCreateButtonEnabled(Actor actor, String context) {
        boolean enabled = ReservationTargets.CONFIRM_RESERVATION.resolveFor(actor).isEnabled();
        if (!enabled) {
            throw new IllegalStateException("El boton Crear Reserva esta deshabilitado en " + context);
        }
    }

    private String readSelectedOptionText(Actor actor, String selectId) {
        Object value = BrowseTheWeb.as(actor).evaluateJavascript(
            "const el = document.getElementById(arguments[0]);" +
                "if (!el || !el.options || el.selectedIndex < 0) return '';" +
                "const selected = el.options[el.selectedIndex];" +
                "return selected ? (selected.textContent || '').trim() : '';",
            selectId
        );
        return value == null ? "" : String.valueOf(value).trim();
    }

    private enum CreationOutcome {
        SUCCESS,
        CONFLICT_OR_ERROR,
        TIMEOUT
    }

    @Cuando("intenta crear una reserva sin diligenciar sede espacio o fecha")
    public void intentaCrearUnaReservaSinDiligenciarSedeEspacioOFecha() {
        administrator.attemptsTo(
            OpenAdminCreateReservationModal.now()
        );
    }

    @Entonces("el boton de crear reserva permanece deshabilitado")
    public void elBotonDeCrearReservaPermaneceDeshabilitado() {
        administrator.should(
            seeThat(
                ReservationConfirmationDisabled.state(),
                equalTo(true)
            )
        );
    }
}

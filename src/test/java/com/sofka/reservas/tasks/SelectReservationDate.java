package com.sofka.reservas.tasks;

import com.sofka.reservas.model.ReservationCriteria;
import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.time.format.TextStyle;
import java.util.Locale;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectReservationDate implements Task {

    private final ReservationCriteria reservationCriteria;

    public SelectReservationDate(ReservationCriteria reservationCriteria) {
        this.reservationCriteria = reservationCriteria;
    }

    public static SelectReservationDate from(ReservationCriteria reservationCriteria) {
        return Tasks.instrumented(SelectReservationDate.class, reservationCriteria);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String expectedMonth = reservationCriteria.date().getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                .toUpperCase(Locale.ROOT);
        String expectedYear = String.valueOf(reservationCriteria.date().getYear());
        String targetDay = reservationCriteria.dayOfMonth();

        for (int attempt = 0; attempt < 12; attempt++) {
            String currentMonth = Text.of(ReservationTargets.CALENDAR_MONTH)
                    .answeredBy(actor)
                    .toUpperCase(Locale.ROOT);
            if (currentMonth.contains(expectedMonth) && currentMonth.contains(expectedYear)) {
                break;
            }
            actor.attemptsTo(Click.on(ReservationTargets.NEXT_MONTH));
        }

        actor.attemptsTo(
            WaitUntil.the(ReservationTargets.availableDay(targetDay), isVisible()).forNoMoreThan(10).seconds(),
            Click.on(ReservationTargets.availableDay(targetDay))
        );
    }
}

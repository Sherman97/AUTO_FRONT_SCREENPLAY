package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;

public class SetReservationEndTime implements Task {

    private final String endTime;

    public SetReservationEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static SetReservationEndTime to(String endTime) {
        return Tasks.instrumented(SetReservationEndTime.class, endTime);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String normalizedTime = normalizeForTimeInput(endTime);
        actor.attemptsTo(
                Clear.field(ReservationTargets.END_TIME),
                Enter.theValue(endTime).into(ReservationTargets.END_TIME)
        );
        BrowseTheWeb.as(actor).evaluateJavascript(
                "const el = document.getElementById('endTime');" +
                        "if (el && el.type === 'time') {" +
                        "  el.value = arguments[0];" +
                        "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "}",
                normalizedTime
        );
    }

    private String normalizeForTimeInput(String timeValue) {
        if (timeValue == null) {
            return "";
        }
        String value = timeValue.trim().toUpperCase();
        if (!value.endsWith("AM") && !value.endsWith("PM")) {
            return value;
        }
        String period = value.substring(value.length() - 2);
        String time = value.substring(0, value.length() - 2).trim();
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        String minutes = parts.length > 1 ? parts[1] : "00";

        if ("AM".equals(period) && hour == 12) {
            hour = 0;
        } else if ("PM".equals(period) && hour < 12) {
            hour += 12;
        }
        return String.format("%02d:%s", hour, minutes);
    }
}


package com.sofka.reservas.tasks;

import com.sofka.reservas.ui.ReservationTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class SetReservationStartTime implements Task {

    private final String startTime;

    public SetReservationStartTime(String startTime) {
        this.startTime = startTime;
    }

    public static SetReservationStartTime to(String startTime) {
        return Tasks.instrumented(SetReservationStartTime.class, startTime);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String normalizedTime = normalizeForTimeInput(startTime);
        String uiFormattedTime = normalizeForTextTimeInput(startTime);
        BrowseTheWeb.as(actor).evaluateJavascript(
                "const el = document.getElementById('startTime');" +
                        "if (el) {" +
                        "  const value = el.type === 'time' ? arguments[0] : arguments[1];" +
                        "  const setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                        "  setter.call(el, value);" +
                        "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('blur', { bubbles: true }));" +
                        "}",
                normalizedTime,
                uiFormattedTime
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

    private String normalizeForTextTimeInput(String timeValue) {
        if (timeValue == null) {
            return "";
        }
        String value = timeValue.trim().toUpperCase();
        if (!value.endsWith("AM") && !value.endsWith("PM")) {
            return timeValue;
        }
        String meridian = value.endsWith("AM") ? "a. m." : "p. m.";
        String time = value.substring(0, value.length() - 2).trim();
        return time + " " + meridian;
    }
}


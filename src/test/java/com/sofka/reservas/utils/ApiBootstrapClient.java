package com.sofka.reservas.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.reservas.model.AuthenticatedSession;
import com.sofka.reservas.model.ReservationCriteria;
import com.sofka.reservas.model.TestUser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ApiBootstrapClient {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiBootstrapClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public AuthenticatedSession createAuthenticatedSession(TestUser user) {
        ensureUserRegistered(user);
        return login(user);
    }

    public void createBlockingReservation(TestUser user, String locationName, ReservationCriteria criteria) {
        AuthenticatedSession session = login(user);
        long spaceId = findSpaceIdByName(session.token(), locationName);
        createReservation(session.token(), spaceId, criteria);
    }

    public ReservationCriteria resolveNextAvailableCriteria(
            String token,
            String locationName,
            ReservationCriteria baseCriteria,
            int maxDaysToSearch
    ) {
        long spaceId = findSpaceIdByName(token, locationName);
        for (int offset = 0; offset <= maxDaysToSearch; offset++) {
            ReservationCriteria candidate = new ReservationCriteria(
                    baseCriteria.date().plusDays(offset),
                    baseCriteria.startTime(),
                    baseCriteria.endTime()
            );
            if (isSpaceAvailableForRange(token, spaceId, candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException(
                "No hay disponibilidad para " + locationName + " en los proximos " + maxDaysToSearch + " dias"
        );
    }

    private void ensureUserRegistered(TestUser user) {
        try {
            String requestBody = objectMapper.writeValueAsString(
                    objectMapper.createObjectNode()
                            .put("name", user.fullName())
                            .put("email", user.email())
                            .put("password", user.password())
            );

            HttpResponse<String> response = send(HttpRequest.newBuilder(URI.create(TestEnvironment.AUTH_REGISTER_ENDPOINT))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build());

            if (response.statusCode() != 201 && response.statusCode() != 409) {
                throw new IllegalStateException("No fue posible registrar el usuario de prueba: " + response.body());
            }
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("No fue posible preparar el usuario de prueba", exception);
        }
    }

    private AuthenticatedSession login(TestUser user) {
        try {
            String requestBody = objectMapper.writeValueAsString(
                    objectMapper.createObjectNode()
                            .put("email", user.email())
                            .put("password", user.password())
            );

            HttpResponse<String> response = send(HttpRequest.newBuilder(URI.create(TestEnvironment.AUTH_LOGIN_ENDPOINT))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("No fue posible autenticar el usuario de prueba: " + response.body());
            }

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode data = root.path("data");
            return new AuthenticatedSession(data.path("token").asText(), data.path("user").toString());
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("No fue posible autenticar el usuario de prueba", exception);
        }
    }

    private long findSpaceIdByName(String token, String locationName) {
        try {
            HttpResponse<String> response = send(HttpRequest.newBuilder(URI.create(TestEnvironment.LOCATIONS_ENDPOINT))
                    .timeout(Duration.ofSeconds(15))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("No fue posible consultar locaciones: " + response.body());
            }

            JsonNode spaces = objectMapper.readTree(response.body()).path("data");
            for (JsonNode space : spaces) {
                if (locationName.equals(space.path("name").asText())) {
                    return space.path("id").asLong();
                }
            }
            throw new IllegalStateException("No se encontró la locación objetivo: " + locationName);
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("No fue posible obtener la locación objetivo", exception);
        }
    }

    private void createReservation(String token, long spaceId, ReservationCriteria criteria) {
        try {
            LocalDateTime startDateTime = criteria.date().atTime(parseTime(criteria.startTime()));
            LocalDateTime endDateTime = criteria.date().atTime(parseTime(criteria.endTime()));

            String requestBody = objectMapper.writeValueAsString(
                    objectMapper.createObjectNode()
                            .put("spaceId", spaceId)
                            .put("startAt", startDateTime.atZone(ZoneId.systemDefault()).format(DATE_TIME_FORMATTER))
                            .put("endAt", endDateTime.atZone(ZoneId.systemDefault()).format(DATE_TIME_FORMATTER))
                            .put("title", "Reserva bloqueante automatizada")
                            .put("attendeesCount", 4)
                            .put("notes", "Reserva creada para preparar escenario negativo")
                            .putArray("equipmentIds")
            );

            HttpResponse<String> response = send(HttpRequest.newBuilder(URI.create(TestEnvironment.BOOKINGS_ENDPOINT))
                    .timeout(Duration.ofSeconds(15))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build());

            if (response.statusCode() != 201 && response.statusCode() != 409) {
                throw new IllegalStateException("No fue posible crear la reserva bloqueante: " + response.body());
            }
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("No fue posible crear la reserva bloqueante", exception);
        }
    }

    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private boolean isSpaceAvailableForRange(String token, long spaceId, ReservationCriteria criteria) {
        try {
            HttpResponse<String> response = send(HttpRequest.newBuilder(URI.create(TestEnvironment.BOOKINGS_ENDPOINT + "?spaceId=" + spaceId))
                    .timeout(Duration.ofSeconds(15))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build());

            if (response.statusCode() != 200) {
                return false;
            }

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode data = root.path("data");
            if (!data.isArray()) {
                data = root;
            }
            if (!data.isArray()) {
                return true;
            }

            LocalDate requestedDate = criteria.date();
            LocalTime requestedStart = parseTime(criteria.startTime());
            LocalTime requestedEnd = parseTime(criteria.endTime());

            for (JsonNode reservation : data) {
                String status = reservation.path("status").asText("").toLowerCase(Locale.ROOT);
                if ("cancelled".equals(status) || "rejected".equals(status) || "completed".equals(status)) {
                    continue;
                }

                String startAt = reservation.path("startAt").asText("");
                String endAt = reservation.path("endAt").asText("");
                if (startAt.isBlank() || endAt.isBlank()) {
                    continue;
                }

                LocalDateTime existingStart = OffsetDateTime.parse(startAt)
                        .atZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime();
                LocalDateTime existingEnd = OffsetDateTime.parse(endAt)
                        .atZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime();

                if (!existingStart.toLocalDate().equals(requestedDate)) {
                    continue;
                }

                boolean overlap = requestedStart.isBefore(existingEnd.toLocalTime())
                        && requestedEnd.isAfter(existingStart.toLocalTime());
                if (overlap) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private LocalTime parseTime(String value) {
        if (value == null) {
            return LocalTime.MIN;
        }
        String trimmed = value.trim().toUpperCase(Locale.ROOT);
        if (!trimmed.endsWith("AM") && !trimmed.endsWith("PM")) {
            return LocalTime.parse(trimmed);
        }

        String meridian = trimmed.substring(trimmed.length() - 2);
        String timePart = trimmed.substring(0, trimmed.length() - 2).trim();
        String[] parts = timePart.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        if ("AM".equals(meridian) && hour == 12) {
            hour = 0;
        } else if ("PM".equals(meridian) && hour < 12) {
            hour += 12;
        }
        return LocalTime.of(hour, minute);
    }
}

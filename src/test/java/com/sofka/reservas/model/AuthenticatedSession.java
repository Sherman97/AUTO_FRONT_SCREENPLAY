package com.sofka.reservas.model;

public record AuthenticatedSession(String token, String userJson) {
}


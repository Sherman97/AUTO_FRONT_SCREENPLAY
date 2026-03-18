package com.sofka.reservas.utils;

public final class TestEnvironment {

    public static final String UI_BASE_URL = "http://localhost:8080";
    public static final String API_BASE_URL = "http://localhost:3000";
    public static final String LOGIN_URL = UI_BASE_URL + "/";
    public static final String DASHBOARD_URL = UI_BASE_URL + "/dashboard";
    public static final String AUTH_REGISTER_ENDPOINT = API_BASE_URL + "/auth/register";
    public static final String AUTH_LOGIN_ENDPOINT = API_BASE_URL + "/auth/login";
    public static final String LOCATIONS_ENDPOINT = API_BASE_URL + "/locations/spaces?activeOnly=true";
    public static final String BOOKINGS_ENDPOINT = API_BASE_URL + "/bookings/reservations";
    public static final String TARGET_LOCATION_NAME = "Sala Andina";

    private TestEnvironment() {
    }
}

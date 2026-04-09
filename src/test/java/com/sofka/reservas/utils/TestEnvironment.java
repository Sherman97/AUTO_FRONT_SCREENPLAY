package com.sofka.reservas.utils;

public final class TestEnvironment {

    public static final String BASE_URL = System.getProperty("webdriver.base.url", "http://localhost:8080");
    public static final String LOGIN_PATH = "/login";
    public static final String ADMIN_PATH = "/admin-reservations";
    private static final String DEFAULT_ADMIN_EMAIL = "prueba.admin@demo.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";

    // Backward-compatible constants kept for existing support utilities.
    public static final String UI_BASE_URL = BASE_URL;
    public static final String API_BASE_URL = BASE_URL;
    public static final String LOGIN_URL = BASE_URL + LOGIN_PATH;
    public static final String DASHBOARD_URL = BASE_URL + "/dashboard";
    public static final String AUTH_REGISTER_ENDPOINT = BASE_URL + "/auth/register";
    public static final String AUTH_LOGIN_ENDPOINT = BASE_URL + "/auth/login";
    public static final String LOCATIONS_ENDPOINT = BASE_URL + "/locations/spaces?activeOnly=true";
    public static final String BOOKINGS_ENDPOINT = BASE_URL + "/bookings/reservations";
    public static final String TARGET_LOCATION_NAME = "Sala Andina";

    private TestEnvironment() {
    }

    public static String loginUrl() {
        return BASE_URL + LOGIN_PATH;
    }

    public static String adminReservationsUrl() {
        return BASE_URL + ADMIN_PATH;
    }

    public static String adminEmail() {
        return System.getProperty("admin.email", DEFAULT_ADMIN_EMAIL);
    }

    public static String adminPassword() {
        return System.getProperty("admin.password", DEFAULT_ADMIN_PASSWORD);
    }
}

package com.sofka.reservas.ui.catalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class ReservationFindByCatalog {

    @FindBy(css = ".modal-content")
    public static WebElement RESERVATION_MODAL;

    @FindBy(css = ".calendar-grid")
    public static WebElement RESERVATION_CALENDAR;

    @FindBy(css = ".search-input")
    public static WebElement SEARCH_INPUT;

    @FindBy(css = ".calendar-header h3")
    public static WebElement CALENDAR_MONTH;

    @FindBy(css = ".calendar-nav-btn:last-of-type")
    public static WebElement NEXT_MONTH;

    @FindBy(id = "startTime")
    public static WebElement START_TIME;

    @FindBy(id = "endTime")
    public static WebElement END_TIME;

    @FindBy(xpath = "//button[normalize-space()='Crear Reserva']")
    public static WebElement CONFIRM_RESERVATION;

    @FindBy(css = ".admin-toast-success, .modal-success-banner")
    public static WebElement SUCCESS_MESSAGE;

    @FindBy(css = ".time-conflict-warning")
    public static WebElement CONFLICT_WARNING;

    @FindBy(css = ".modal-error-banner")
    public static WebElement RESERVATION_ERROR;

    @FindBy(css = ".admin-page-header h1")
    public static WebElement ADMIN_PAGE_TITLE;

    @FindBy(xpath = "//button[normalize-space()='Nueva reserva']")
    public static WebElement NEW_RESERVATION_BUTTON;

    @FindBy(css = ".modal-content.admin-create-modal")
    public static WebElement ADMIN_CREATE_MODAL;

    @FindBy(id = "admin-user-requester")
    public static WebElement USER_REQUESTER_INPUT;

    @FindBy(css = ".admin-autocomplete-list")
    public static WebElement USER_SUGGESTION_LIST;

    @FindBy(id = "admin-create-site")
    public static WebElement SITE_SELECT;

    @FindBy(id = "admin-create-space")
    public static WebElement SPACE_SELECT;

    @FindBy(css = ".calendar-header .calendar-nav-btn:last-of-type")
    public static WebElement NEXT_MONTH_BUTTON;

    @FindBy(css = ".calendar-header .calendar-nav-btn:first-of-type")
    public static WebElement PREVIOUS_MONTH_BUTTON;

    @FindBy(xpath = "//button[normalize-space()='Crear Reserva']")
    public static WebElement CREATE_RESERVATION_BUTTON;

    @FindBy(css = ".admin-reservations-table tbody tr")
    public static WebElement ADMIN_TABLE_ROWS;

    @FindBy(css = ".admin-reservations-table tbody tr:first-child td")
    public static WebElement ADMIN_FIRST_ROW_CELLS;

    @FindBy(css = ".admin-toast")
    public static WebElement ADMIN_TOAST;

    @FindBy(css = ".admin-toast-error")
    public static WebElement ADMIN_ERROR_TOAST;

    @FindBy(css = ".calendar-day.selected")
    public static WebElement SELECTED_CALENDAR_DAY;

    // Dynamic templates expressed with @FindBy so selector sources remain centralized in this catalog.
    @FindBy(xpath = "//div[contains(@class,'item-card')][.//h3[normalize-space()='%s']]//button[contains(.,'Reservar')]")
    public static WebElement RESERVE_BUTTON_FOR_TEMPLATE;

    @FindBy(xpath = "//div[contains(@class,'calendar-day') and contains(@class,'available') and normalize-space()='%s']")
    public static WebElement AVAILABLE_DAY_TEMPLATE;

    @FindBy(xpath = "//div[contains(@class,'calendar-day') and contains(@class,'selected') and normalize-space()='%s']")
    public static WebElement SELECTED_DAY_TEMPLATE;

    @FindBy(css = ".admin-autocomplete-list .admin-autocomplete-item:nth-of-type(%d)")
    public static WebElement ADMIN_USER_SUGGESTION_AT_TEMPLATE;

    @FindBy(xpath = "(//div[contains(@class,'calendar-day') and contains(@class,'available') and not(contains(@class,'past'))])[1]")
    public static WebElement FIRST_AVAILABLE_CALENDAR_DAY;

    @FindBy(xpath = "//div[contains(@class,'calendar-day') and contains(@class,'available') and normalize-space()='%d']")
    public static WebElement DAY_IN_CALENDAR_TEMPLATE;

    private ReservationFindByCatalog() {
    }
}

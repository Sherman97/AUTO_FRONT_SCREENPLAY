package com.sofka.reservas.ui.catalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class LoginFindByCatalog {

    @FindBy(id = "email")
    public static WebElement EMAIL;

    @FindBy(id = "password")
    public static WebElement PASSWORD;

    @FindBy(css = "button[type='submit']")
    public static WebElement SUBMIT;

    private LoginFindByCatalog() {
    }
}

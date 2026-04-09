package com.sofka.reservas.ui.support;

import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public final class FindByLocator {

    private FindByLocator() {
    }

    public static String of(Class<?> catalogClass, String fieldName) {
        return resolve(catalogClass, fieldName);
    }

    public static String template(Class<?> catalogClass, String fieldName, Object... args) {
        return String.format(resolve(catalogClass, fieldName), args);
    }

    private static String resolve(Class<?> catalogClass, String fieldName) {
        try {
            Field field = catalogClass.getDeclaredField(fieldName);
            FindBy findBy = field.getAnnotation(FindBy.class);
            if (findBy == null) {
                throw new IllegalStateException("Field " + fieldName + " has no @FindBy in " + catalogClass.getSimpleName());
            }

            if (!findBy.css().isBlank()) return findBy.css();
            if (!findBy.xpath().isBlank()) return findBy.xpath();
            if (!findBy.id().isBlank()) return "#" + findBy.id();
            if (!findBy.name().isBlank()) return "[name='" + findBy.name() + "']";
            if (!findBy.className().isBlank()) return "." + findBy.className();
            if (!findBy.tagName().isBlank()) return findBy.tagName();
            if (!findBy.linkText().isBlank()) return "//a[normalize-space()='" + findBy.linkText() + "']";
            if (!findBy.partialLinkText().isBlank()) return "//a[contains(normalize-space(),'" + findBy.partialLinkText() + "')]";

            throw new IllegalStateException("@FindBy on field " + fieldName + " has no supported locator strategy");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Field " + fieldName + " not found in " + catalogClass.getSimpleName(), e);
        }
    }
}

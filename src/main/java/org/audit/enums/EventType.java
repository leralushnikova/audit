package org.audit.enums;

import lombok.Getter;

/**
 * Enum класс EventType для определения событий
 */
@Getter
public enum EventType {
    CREATE_ACCOUNT("Create Account"),
    UPDATE_PROFILE("Update Profile"),
    DELETE_ACCOUNT("Delete Account");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

}
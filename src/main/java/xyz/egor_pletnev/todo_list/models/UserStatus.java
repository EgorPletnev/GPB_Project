package xyz.egor_pletnev.todo_list.models;

import lombok.Getter;

@Getter
public enum UserStatus {
    PENDING("Ожидает подтверждения"),
    ACTIVE("Активен"),
    BANNED("Заблокирован"),
    INACTIVE("Неактивен"),
    DELETED("Удален");

    private final String title;

    UserStatus(String title) {
        this.title = title;
    }
}
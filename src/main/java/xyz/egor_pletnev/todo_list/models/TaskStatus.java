package xyz.egor_pletnev.todo_list.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    NEW("Новая задача"),
    IN_PROGRESS("В работе"),
    DONE("Готова"),
    CANCELLED("Отменена"),
    DELETED("Удалено");

    private final String title;

    TaskStatus(String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {return title;}
}
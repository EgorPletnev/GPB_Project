package xyz.egor_pletnev.todo_list.dto;

import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

public record TaskEditResponse(
        String title,
        String description,
        Boolean isOutdoor,
        Integer priority,
        Double latitude,
        Double longitude,
        Double minTemp,
        Double maxTemp,
        WeatherStatus requiredWeather,
        LocalDateTime dueDate
) {}
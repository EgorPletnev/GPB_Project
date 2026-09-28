package xyz.egor_pletnev.todo_list.dto;

import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        String id,
        String title,
        String description,
        Integer priority,
        TaskStatus status,
        Boolean isOutdoor,
        WeatherStatus requiredWeather,
        Double latitude,
        Double longitude,
        Double minTemp,
        Double maxTemp,
        WeatherStatus forecastWeather,
        Double forecastTemp,
        LocalDateTime dueDate,
        LocalDateTime createdAt
) {}
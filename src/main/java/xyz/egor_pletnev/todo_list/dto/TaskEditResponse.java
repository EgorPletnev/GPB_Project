package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.Size;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

public record TaskEditResponse (
        String title,
        String description,
        Boolean isOutdoor,

        Double latitude,
        Double longitude,

        @Size(min = -100, max = 100)
        Double minTemp,

        @Size(min = -100, max = 100)
        Double maxTemp,

        WeatherStatus requiredWeather,
        LocalDateTime dueDate
) { }

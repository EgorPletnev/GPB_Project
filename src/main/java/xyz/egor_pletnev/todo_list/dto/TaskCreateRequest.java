package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        @NotBlank
        String userId,

        Integer priority,

        @NotBlank
        @Size(max = 100)
        String title,

        @Size(max = 1000)
        String description,

        @NotNull
        Boolean isOutdoor,

        Double latitude,
        Double longitude,

        @Size(min = -100, max = 100)
        Double minTemp,

        @Size(min = -100, max = 100)
        Double maxTemp,

        WeatherStatus requiredWeather,
        LocalDateTime dueDate,

        WeatherStatus forecastWeather,
        Double forecastTemp
) {}
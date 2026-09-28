package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        Double latitude,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        Double longitude,

        @DecimalMin("-100.0")
        @DecimalMax("100.0")
        Double minTemp,

        @DecimalMin("-100.0")
        @DecimalMax("100.0")
        Double maxTemp,

        WeatherStatus requiredWeather,
        LocalDateTime dueDate,
        WeatherStatus forecastWeather,
        Double forecastTemp
) {}
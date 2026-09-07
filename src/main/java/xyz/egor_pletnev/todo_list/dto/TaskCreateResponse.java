package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDate;

public record TaskCreateResponse (
        @NotNull
        String title,

        @NotNull
        String description,

        @NotNull
        TaskStatus taskStatus,

        @NotNull
        LocalDate createDate,

        @NotNull
        Boolean isOutdoor,

        WeatherStatus forecastWeather,
        Double forecastTemp,

        @Size(min = -100, max = 100)
        Double minTemp,

        @Size(min = -100, max = 100)
        Double maxTemp,

        WeatherStatus requiredWeather
) { }


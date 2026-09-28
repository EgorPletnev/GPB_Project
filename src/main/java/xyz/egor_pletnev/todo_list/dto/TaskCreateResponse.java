package xyz.egor_pletnev.todo_list.dto;

import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

public record TaskCreateResponse(
        String title,
        String description,
        TaskStatus taskStatus,
        LocalDateTime createDate,
        Boolean isOutdoor,
        WeatherStatus forecastWeather,
        Double forecastTemp,
        Double minTemp,
        Double maxTemp,
        WeatherStatus requiredWeather
) {}
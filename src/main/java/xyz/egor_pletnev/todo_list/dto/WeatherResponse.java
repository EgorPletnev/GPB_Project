package xyz.egor_pletnev.todo_list.dto;

import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

public record WeatherResponse(
        Double lat,
        Double lon,
        LocalDateTime date,
        WeatherStatus condition,
        Double temperature
) {}

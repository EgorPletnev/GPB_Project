package xyz.egor_pletnev.todo_list.service;

import xyz.egor_pletnev.todo_list.dto.WeatherResponse;

import java.time.LocalDateTime;

public interface WeatherService {
    WeatherResponse getWeather(Double lat, Double lon, LocalDateTime date);
}

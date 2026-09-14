package xyz.egor_pletnev.todo_list.manager;

import xyz.egor_pletnev.todo_list.dto.WeatherResponse;

import java.time.LocalDateTime;


public interface WeatherManager {
    WeatherResponse getWeather(Double lat, Double lon, LocalDateTime date);
}

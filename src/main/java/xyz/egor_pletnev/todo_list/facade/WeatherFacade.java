package xyz.egor_pletnev.todo_list.facade;

import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;

import java.time.LocalDateTime;

public interface WeatherFacade {
    OpenMeteoResponse getOpenMeteoWeather(Double lat, Double lon, LocalDateTime date);
}

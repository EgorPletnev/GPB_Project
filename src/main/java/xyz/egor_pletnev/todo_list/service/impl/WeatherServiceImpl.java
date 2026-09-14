package xyz.egor_pletnev.todo_list.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;
import xyz.egor_pletnev.todo_list.dto.WeatherResponse;
import xyz.egor_pletnev.todo_list.manager.WeatherManager;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;
import xyz.egor_pletnev.todo_list.service.WeatherService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final WeatherManager weatherManager;

    @Override
    public WeatherResponse getWeather(Double lat, Double lon, LocalDateTime date) {
        return weatherManager.getWeather(lat, lon, date);

    }
}


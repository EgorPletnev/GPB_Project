package xyz.egor_pletnev.todo_list.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;
import xyz.egor_pletnev.todo_list.dto.WeatherResponse;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;
import xyz.egor_pletnev.todo_list.service.WeatherService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");

    private final RestClient weatherRestClient;

    @Override
    public WeatherResponse getWeather(Double lat, Double lon, LocalDateTime date) { // TODO: всё что отвечает за вызов стороннего сервиса перенести в фасад
        String hourStr = date.truncatedTo(ChronoUnit.HOURS).format(FORMATTER);

        log.info("Запрос погоды: lat={}, lon={}, hour={}", lat, lon, hourStr);

        OpenMeteoResponse response = weatherRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("hourly", "temperature_2m,weather_code")
                        .queryParam("start_hour", hourStr)
                        .queryParam("end_hour", hourStr)
                        .build())
                .retrieve()
                .body(OpenMeteoResponse.class);

        if (response == null || response.hourly() == null ||
                response.hourly().weatherCode().isEmpty() || response.hourly().temperature().isEmpty()) {
            throw new RuntimeException("Не удалось получить данные о погоде от сервиса");
        }

        int rawCode = response.hourly().weatherCode().getFirst();
        Double temperature = response.hourly().temperature().getFirst();

        WeatherStatus weatherCode = WeatherStatus.weatherStatusFromCode(rawCode);

        return new WeatherResponse(
                lat,
                lon,
                date,
                weatherCode,
                temperature
        );
    }
}


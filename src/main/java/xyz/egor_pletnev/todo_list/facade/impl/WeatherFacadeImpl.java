package xyz.egor_pletnev.todo_list.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;
import xyz.egor_pletnev.todo_list.facade.WeatherFacade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherFacadeImpl implements WeatherFacade {
    private final RestClient weatherRestClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");

    @Override
    public OpenMeteoResponse getOpenMeteoWeather(Double lat, Double lon, LocalDateTime date) { //TODO: сделать получение date без форматирования
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

        if (response == null || response.hourly() == null || //TODO: разделить условие на более мелкие
                response.hourly().weatherCode().isEmpty() || response.hourly().temperature().isEmpty()) {
            throw new RuntimeException("Не удалось получить данные о погоде от сервиса");
        }

        return response;
    }
}

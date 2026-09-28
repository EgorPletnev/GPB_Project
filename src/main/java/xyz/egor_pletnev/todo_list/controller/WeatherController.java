package xyz.egor_pletnev.todo_list.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.egor_pletnev.todo_list.dto.WeatherResponse;
import xyz.egor_pletnev.todo_list.service.WeatherService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "Получение погоды по координатам широты, долготы и даты")
    @ApiResponse(responseCode = "200", description = "Погода успешно получена")
    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> getWeatherByCoordinates(
            @Parameter(description = "Широта координаты", required = true)
            @RequestParam
            Double lat,

            @Parameter(description = "Долгота координаты", required = true)
            @RequestParam
            Double lon,

            @Parameter(description = "Дата для получения погоды", required = true)
            @RequestParam
            LocalDateTime dateTime
    ) {
        return ResponseEntity
                .ok()
                .body(weatherService.getWeather(lat, lon, dateTime));
    }
}
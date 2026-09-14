package xyz.egor_pletnev.todo_list.manager.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;
import xyz.egor_pletnev.todo_list.dto.WeatherResponse;
import xyz.egor_pletnev.todo_list.facade.WeatherFacade;
import xyz.egor_pletnev.todo_list.manager.WeatherManager;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherManagerImpl implements WeatherManager {
    private final WeatherFacade weatherFacade;

    @Override
    public WeatherResponse getWeather(Double lat, Double lon, LocalDateTime date) {
        OpenMeteoResponse openMeteoResponse = weatherFacade.getOpenMeteoWeather(lat, lon, date);

        int rawCode = openMeteoResponse.hourly().weatherCode().getFirst();
        Double temperature = openMeteoResponse.hourly().temperature().getFirst();

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

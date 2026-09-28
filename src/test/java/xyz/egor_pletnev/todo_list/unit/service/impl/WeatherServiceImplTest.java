package xyz.egor_pletnev.todo_list.unit.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.egor_pletnev.todo_list.dto.WeatherResponse;
import xyz.egor_pletnev.todo_list.manager.WeatherManager;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;
import xyz.egor_pletnev.todo_list.service.impl.WeatherServiceImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private WeatherManager weatherManager;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void getWeather_success() {
        Double lat = 55.75;
        Double lon = 37.61;
        LocalDateTime date = LocalDateTime.of(2026, 10, 15, 12, 0);

        WeatherResponse expectedResponse = new WeatherResponse(
                lat, lon, date, WeatherStatus.CODE_0, 20.0
        );

        when(weatherManager.getWeather(lat, lon, date)).thenReturn(expectedResponse);

        WeatherResponse actualResponse = weatherService.getWeather(lat, lon, date);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(weatherManager).getWeather(lat, lon, date);
    }

    @Test
    void getWeather_managerThrowsException() {
        Double lat = 55.75;
        Double lon = 37.61;
        LocalDateTime date = LocalDateTime.of(2026, 10, 15, 12, 0);

        when(weatherManager.getWeather(lat, lon, date))
                .thenThrow(new RuntimeException("Ошибка получения погоды"));

        assertThrows(RuntimeException.class, () -> weatherService.getWeather(lat, lon, date));
    }
}
package xyz.egor_pletnev.todo_list.unit.manager.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;
import xyz.egor_pletnev.todo_list.dto.WeatherResponse;
import xyz.egor_pletnev.todo_list.facade.WeatherFacade;
import xyz.egor_pletnev.todo_list.manager.impl.WeatherManagerImpl;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherManagerImplTest {

    @Mock
    private WeatherFacade weatherFacade;

    @InjectMocks
    private WeatherManagerImpl weatherManager;

    private Double lat;
    private Double lon;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        lat = 55.7558;
        lon = 37.6173;
        testDate = LocalDateTime.of(2026, 10, 15, 12, 0);
    }

    @Test
    void getWeather_knownCode_success() {
        OpenMeteoResponse.HourlyData hourly = new OpenMeteoResponse.HourlyData(
                List.of("2026-10-15T12:00"),
                List.of(22.5),
                List.of(0)
        );
        OpenMeteoResponse openMeteoResponse = new OpenMeteoResponse(hourly);

        when(weatherFacade.getOpenMeteoWeather(lat, lon, testDate)).thenReturn(openMeteoResponse);

        WeatherResponse response = weatherManager.getWeather(lat, lon, testDate);

        assertNotNull(response);
        assertEquals(lat, response.lat());
        assertEquals(lon, response.lon());
        assertEquals(testDate, response.date());
        assertEquals(WeatherStatus.CODE_0, response.condition());
        assertEquals(22.5, response.temperature());
        verify(weatherFacade).getOpenMeteoWeather(lat, lon, testDate);
    }

    @Test
    void getWeather_rainCode_success() {
        OpenMeteoResponse.HourlyData hourly = new OpenMeteoResponse.HourlyData(
                List.of("2026-10-15T12:00"),
                List.of(11.0),
                List.of(61)
        );
        OpenMeteoResponse openMeteoResponse = new OpenMeteoResponse(hourly);

        when(weatherFacade.getOpenMeteoWeather(lat, lon, testDate)).thenReturn(openMeteoResponse);

        WeatherResponse response = weatherManager.getWeather(lat, lon, testDate);

        assertNotNull(response);
        assertEquals(WeatherStatus.CODE_61, response.condition());
        assertEquals(11.0, response.temperature());
    }

    @Test
    void getWeather_unknownCode_returnsUnknown() {
        OpenMeteoResponse.HourlyData hourly = new OpenMeteoResponse.HourlyData(
                List.of("2026-10-15T12:00"),
                List.of(-5.0),
                List.of(9999)
        );
        OpenMeteoResponse openMeteoResponse = new OpenMeteoResponse(hourly);

        when(weatherFacade.getOpenMeteoWeather(lat, lon, testDate)).thenReturn(openMeteoResponse);

        WeatherResponse response = weatherManager.getWeather(lat, lon, testDate);

        assertNotNull(response);
        assertEquals(WeatherStatus.UNKNOWN, response.condition());
        assertEquals(-5.0, response.temperature());
    }

    @Test
    void getWeather_facadeThrowsException() {
        when(weatherFacade.getOpenMeteoWeather(lat, lon, testDate))
                .thenThrow(new RuntimeException("Не удалось получить данные о погоде от сервиса"));

        assertThrows(RuntimeException.class, () -> weatherManager.getWeather(lat, lon, testDate));
    }
}
package xyz.egor_pletnev.todo_list.integration.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import xyz.egor_pletnev.todo_list.dto.OpenMeteoResponse;
import xyz.egor_pletnev.todo_list.facade.impl.WeatherFacadeImpl;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class WeatherFacadeImplTest {

    private MockRestServiceServer mockServer;
    private WeatherFacadeImpl weatherFacade;

    private final Double lat = 55.7558;
    private final Double lon = 37.6173;
    private final LocalDateTime testDateTime = LocalDateTime.of(2026, 7, 20, 15, 42, 30);
    private final String expectedHourStr = "2026-07-20T15:00";

    @BeforeEach
    void setUp() {
        RestClient.Builder restClientBuilder = RestClient.builder()
                .baseUrl("https://api.open-meteo.com/v1");

        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();

        RestClient restClient = restClientBuilder.build();
        weatherFacade = new WeatherFacadeImpl(restClient);
    }

    @Test
    void getOpenMeteoWeather_success() {
        String mockJsonResponse = """
                {
                  "hourly": {
                    "time": ["2026-07-20T15:00"],
                    "temperature_2m": [24.5],
                    "weather_code": [1]
                  }
                }
                """;

        mockServer.expect(requestTo(startsWith("https://api.open-meteo.com/v1/forecast")))
                .andExpect(method(HttpMethod.GET))
                .andExpect(queryParam("latitude", lat.toString()))
                .andExpect(queryParam("longitude", lon.toString()))
                .andExpect(queryParam("hourly", "temperature_2m,weather_code"))
                .andExpect(queryParam("start_hour", expectedHourStr))
                .andExpect(queryParam("end_hour", expectedHourStr))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON));

        OpenMeteoResponse response = weatherFacade.getOpenMeteoWeather(lat, lon, testDateTime);

        mockServer.verify();
        assertNotNull(response);
        assertNotNull(response.hourly());
        assertEquals(1, response.hourly().weatherCode().getFirst());
        assertEquals(24.5, response.hourly().temperature().getFirst());
    }

    @Test
    void getOpenMeteoWeather_nullHourly() {
        String emptyJsonResponse = "{}";

        mockServer.expect(requestTo(startsWith("https://api.open-meteo.com/v1/forecast")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(emptyJsonResponse, MediaType.APPLICATION_JSON));

        assertThrows(RuntimeException.class, () -> weatherFacade.getOpenMeteoWeather(lat, lon, testDateTime));
        mockServer.verify();
    }

    @Test
    void getOpenMeteoWeather_emptyWeatherCode() {
        String jsonWithEmptyWeatherCode = """
                {
                  "hourly": {
                    "time": ["2026-07-20T15:00"],
                    "temperature_2m": [24.5],
                    "weather_code": []
                  }
                }
                """;

        mockServer.expect(requestTo(startsWith("https://api.open-meteo.com/v1/forecast")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonWithEmptyWeatherCode, MediaType.APPLICATION_JSON));

        assertThrows(RuntimeException.class, () -> weatherFacade.getOpenMeteoWeather(lat, lon, testDateTime));
        mockServer.verify();
    }

    @Test
    void getOpenMeteoWeather_emptyTemperature() {
        String jsonWithEmptyTemperature = """
                {
                  "hourly": {
                    "time": ["2026-07-20T15:00"],
                    "temperature_2m": [],
                    "weather_code": [0]
                  }
                }
                """;

        mockServer.expect(requestTo(startsWith("https://api.open-meteo.com/v1/forecast")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonWithEmptyTemperature, MediaType.APPLICATION_JSON));

        assertThrows(RuntimeException.class, () -> weatherFacade.getOpenMeteoWeather(lat, lon, testDateTime));
        mockServer.verify();
    }

    @Test
    void getOpenMeteoWeather_serverError() {
        mockServer.expect(requestTo(startsWith("https://api.open-meteo.com/v1/forecast")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        assertThrows(HttpServerErrorException.class, () -> weatherFacade.getOpenMeteoWeather(lat, lon, testDateTime));
        mockServer.verify();
    }
}
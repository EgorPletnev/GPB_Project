package xyz.egor_pletnev.todo_list.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenMeteoResponse(
        HourlyData hourly
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HourlyData(
            List<String> time,

            @JsonProperty("temperature_2m")
            List<Double> temperature,

            @JsonProperty("weather_code")
            List<Integer> weatherCode
    ) {}
}
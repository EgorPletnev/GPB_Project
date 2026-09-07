package xyz.egor_pletnev.todo_list.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WeatherConfig {

    @Bean
    public RestClient weatherRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.open-meteo.com/v1")
                .build();
    }
}

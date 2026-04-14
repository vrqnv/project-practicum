package ru.tbank.practicum.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableScheduling
public class WeatherScheduler {

    private static final Logger log = LoggerFactory.getLogger(WeatherScheduler.class);
    private final WebClient webClient;

    private static final String API_KEY = "f63d5b22853762d7bc305c0fae622f85";
    private static final double LAT = 51.5406;
    private static final double LON = 46.0086;

    public WeatherScheduler() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .build();
    }

    @Scheduled(fixedRate = 30000)
    public void fetchWeather() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", LAT)
                        .queryParam("lon", LON)
                        .queryParam("units", "metric")
                        .queryParam("lang", "ru")
                        .queryParam("appid", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> log.info("Погода в Саратове: {}", response));
    }
}
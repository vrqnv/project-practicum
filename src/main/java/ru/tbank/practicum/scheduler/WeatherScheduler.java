package ru.tbank.practicum.scheduler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.repository.RoomRepository;
import ru.tbank.practicum.service.BatteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@EnableScheduling
public class WeatherScheduler {

    private static final Logger log = LoggerFactory.getLogger(WeatherScheduler.class);
    private final WebClient webClient;
    private final BatteryService batteryService;
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${weather.api.key}")
    private String apiKey;

    private static final double LAT = 51.5406;
    private static final double LON = 46.0086;

    public WeatherScheduler(BatteryService batteryService, RoomRepository roomRepository) {
        this.batteryService = batteryService;
        this.roomRepository = roomRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .build();
    }

    @Scheduled(fixedRate = 60000)
    public void fetchWeather() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", LAT)
                        .queryParam("lon", LON)
                        .queryParam("units", "metric")
                        .queryParam("lang", "ru")
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> {
                            try {
                                JsonNode root = objectMapper.readTree(response);
                                double outdoorTemp = root.path("main").path("temp").asDouble();
                                int newBatteryTemp;
                                if (outdoorTemp < 0) {
                                    newBatteryTemp = 25;
                                } else {
                                    newBatteryTemp = 18;
                                }
                                List<RoomEntity> rooms = roomRepository.findAll();
                                for (RoomEntity room : rooms) {
                                    batteryService.setTemperature(room.getId(), newBatteryTemp);
                                }
                            } catch (Exception e) {
                                log.error("Ошибка парсинга погоды: {}", e.getMessage());
                            }
                        }
                );
    }
}
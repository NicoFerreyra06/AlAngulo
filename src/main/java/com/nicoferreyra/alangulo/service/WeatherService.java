package com.nicoferreyra.alangulo.service;

import com.nicoferreyra.alangulo.dtos.responseDTO.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String apiUrl;
    @Value("${weather.api.key}")
    private String apiKey;

    @Cacheable(value = "weather")
    public WeatherResponseDTO getWeather(Double latitude, Double longitude) {

        String string = apiUrl + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric";

        return restTemplate.getForObject(string, WeatherResponseDTO.class);
    }

}

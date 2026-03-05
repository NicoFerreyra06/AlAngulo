package com.nicoferreyra.alangulo.dtos.responseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class WeatherResponseDTO {

    private String name;
    @JsonProperty("weather")
    private List<WeatherData> weather;
    @JsonProperty("main")
    private MainData mainData;

    @Data
    public static class WeatherData{
        private Long id;
        private String main;
        private String description;
    }

    @Data
    public static class MainData{
        private Double temp;
        private Integer humidity;
    }
}

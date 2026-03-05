package com.nicoferreyra.alangulo.dtos.responseDTO;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplexResponseDTO {

    private Long id;
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private LocalTime openTime;
    private LocalTime closeTime;
}

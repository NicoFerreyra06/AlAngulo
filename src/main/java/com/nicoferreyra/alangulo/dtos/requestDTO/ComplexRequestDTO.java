package com.nicoferreyra.alangulo.dtos.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplexRequestDTO {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank
    @Size(min = 1, max = 100)
    private String address;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    @NotNull
    private LocalTime openTime;
    @NotNull
    private LocalTime closeTime;
}

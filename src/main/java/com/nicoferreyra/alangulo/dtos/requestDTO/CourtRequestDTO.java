package com.nicoferreyra.alangulo.dtos.requestDTO;

import com.nicoferreyra.alangulo.enums.eTypeCourt;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourtRequestDTO {

    @NotBlank
    private String name;
    @NotNull
    private eTypeCourt typeCourt;
    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal price;

    @NotNull
    private Long idComplex;
}

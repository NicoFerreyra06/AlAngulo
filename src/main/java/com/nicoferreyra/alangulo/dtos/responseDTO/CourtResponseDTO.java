package com.nicoferreyra.alangulo.dtos.responseDTO;

import com.nicoferreyra.alangulo.enums.eTypeCourt;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponseDTO {

    private String complexName;
    private Long complexId;
    private Long courtId;
    private String name;
    private eTypeCourt type;
    private BigDecimal price;

}

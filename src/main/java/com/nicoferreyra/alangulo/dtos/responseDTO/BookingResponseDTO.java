package com.nicoferreyra.alangulo.dtos.responseDTO;

import com.nicoferreyra.alangulo.enums.eStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private String nameComplex;
    private String nameCourt;
    private eStatus bookingStatus;
    private BigDecimal price;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}

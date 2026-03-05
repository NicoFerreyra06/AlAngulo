package com.nicoferreyra.alangulo.dtos.requestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {

    @NotNull
    private Long courtId;

    @NotNull
    @FutureOrPresent (message = "La fecha no puede ser en el pasado")
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}

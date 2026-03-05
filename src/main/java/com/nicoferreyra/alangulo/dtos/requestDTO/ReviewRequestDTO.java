package com.nicoferreyra.alangulo.dtos.requestDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {

    @Size(min = 1, max = 50)
    private String comment;
    @NotNull
    @Min(1)
    @Max(5)
    private int score;

    @NotNull
    private Long complexId;
}

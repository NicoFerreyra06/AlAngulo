package com.nicoferreyra.alangulo.dtos.responseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDTO {
    private Long userId;
    private Long reviewId;
    private String comment;
    private int puntuacion;
    private Double avgCourt;
}

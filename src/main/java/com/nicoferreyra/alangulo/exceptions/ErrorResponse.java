package com.nicoferreyra.alangulo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ErrorResponse {

    private String message;
    private String timestamp;
}

package com.nicoferreyra.alangulo.model;

import com.nicoferreyra.alangulo.enums.eTypeCourt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private eTypeCourt typeCourt;
    
    private BigDecimal price;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn (name = "complex_id")
    private Complex complex;
}

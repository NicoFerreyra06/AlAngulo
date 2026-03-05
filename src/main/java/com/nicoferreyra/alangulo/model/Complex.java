package com.nicoferreyra.alangulo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Complex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Boolean active;

    private Double longitude;
    private Double latitude;

    private LocalTime openTime;
    private LocalTime closeTime;

    @OneToMany(mappedBy = "complex", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Court> courts;
}

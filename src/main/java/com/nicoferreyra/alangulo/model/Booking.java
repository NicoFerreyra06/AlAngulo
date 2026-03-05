package com.nicoferreyra.alangulo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicoferreyra.alangulo.enums.eStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.web.JsonPath;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    @Column (nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column (nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Column (nullable = false)
    @Min(1)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "court_id")
    @JsonIgnore
    private Court court;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private eStatus status;
}

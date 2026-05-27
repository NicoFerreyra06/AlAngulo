package com.nicoferreyra.alangulo.controller;

import com.nicoferreyra.alangulo.dtos.requestDTO.CourtRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.CourtResponseDTO;
import com.nicoferreyra.alangulo.service.CourtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/court")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @PostMapping
    public ResponseEntity<CourtResponseDTO> createCourt(@Valid @RequestBody CourtRequestDTO complex){
        return new ResponseEntity<>(courtService.createCourt(complex), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CourtResponseDTO>> getAllCourts() {
        return new ResponseEntity<>(courtService.getAllCourts(), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CourtResponseDTO>> getAvailableCourts(@Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
                                                                    @Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                                                    @Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return new ResponseEntity<>(courtService.findAvailableCourts(date, startTime, endTime), HttpStatus.OK);
    }
}

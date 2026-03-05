package com.nicoferreyra.alangulo.service;

import com.nicoferreyra.alangulo.dtos.requestDTO.CourtRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.CourtResponseDTO;
import com.nicoferreyra.alangulo.exceptions.NotFoundException;
import com.nicoferreyra.alangulo.model.Complex;
import com.nicoferreyra.alangulo.model.Court;
import com.nicoferreyra.alangulo.repository.ComplexRepository;
import com.nicoferreyra.alangulo.repository.CourtRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;

    private final ComplexRepository  complexRepository;

    @Transactional
    public CourtResponseDTO createCourt(CourtRequestDTO courtRequestDTO) {

        Court court = mapRequest(courtRequestDTO);
        Court savedCourt = courtRepository.save(court);

        return mapResponse(savedCourt);
    }
    public List<CourtResponseDTO> findAvailableCourts(LocalDate date, LocalTime startTime, LocalTime endTime) {

        List<Court> courts = courtRepository.findAvailableCourts(date, startTime, endTime);
        return courts.stream().map(this::mapResponse).toList();
    }

    public List<CourtResponseDTO> getAllCourts() {
        return courtRepository.findAll().stream()
                .map(court -> CourtResponseDTO.builder()
                        .courtId(court.getId())
                        .price(court.getPrice())
                        .type(court.getTypeCourt())
                        .complexId(court.getComplex().getId())
                        .complexName(court.getComplex().getName())
                        .name(court.getName())
                        .build())
                .toList();
    }
    private Court mapRequest (CourtRequestDTO courtRequestDTO) {
        Complex complex = complexRepository.findById(courtRequestDTO.getIdComplex())
                .orElseThrow(()-> new NotFoundException("Complex not found"));

        return Court.builder()
                .complex(complex)
                .price(courtRequestDTO.getPrice())
                .typeCourt(courtRequestDTO.getTypeCourt())
                .name(courtRequestDTO.getName())
                .build();
    }

    private CourtResponseDTO mapResponse(Court court) {

        return CourtResponseDTO.builder()
                .courtId(court.getId())
                .price(court.getPrice())
                .type(court.getTypeCourt())
                .complexId(court.getComplex().getId())
                .complexName(court.getComplex().getName())
                .name(court.getName())
                .build();
    }
}

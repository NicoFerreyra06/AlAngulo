package com.nicoferreyra.alangulo.service;

import com.nicoferreyra.alangulo.dtos.requestDTO.ComplexRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.ComplexResponseDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.WeatherResponseDTO;
import com.nicoferreyra.alangulo.exceptions.BadRequestException;
import com.nicoferreyra.alangulo.exceptions.NotFoundException;
import com.nicoferreyra.alangulo.model.Complex;
import com.nicoferreyra.alangulo.repository.ComplexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplexService {

    private final ComplexRepository complexRepository;
    private final WeatherService weatherService;

    public ComplexResponseDTO createComplex (ComplexRequestDTO complexRequestDTO) {

        Complex complex = mapRequest(complexRequestDTO);
        Complex complexSaved = complexRepository.save(complex);

        return mapResponse(complexSaved);
    }

    public ComplexResponseDTO findById (Long complexId) {
        Complex complexSaved = complexRepository.findById(complexId)
                .orElseThrow(()-> new NotFoundException("Complex not found"));

        return mapResponse(complexSaved);
    }

    public Page<ComplexResponseDTO> getAllComplexes(Pageable pageable) {
        return complexRepository.findAll(pageable).map(this::mapResponse);
    }

    public void deleteComplex(Long complexId) {
        if (complexRepository.findById(complexId).isPresent()) {
            complexRepository.deleteById(complexId);
        } else {
            throw new NotFoundException("Complex not found");
        }
    }

    public List<ComplexResponseDTO> findNearby (double lat, double lon, double radius) {
        List<Complex> complexSaved = complexRepository.findNearby(lat, lon, radius);

        if (complexSaved.isEmpty()) {
            throw new BadRequestException("Complexes not found");
        }
        return complexSaved.stream()
                .map(this::mapResponse).toList();
    }

    public WeatherResponseDTO getWeatherByComplexId(Long complexId) {
        Complex complex = complexRepository.findById(complexId)
                .orElseThrow(()-> new NotFoundException("Complex not found"));

        if (complex.getLongitude() == null || complex.getLatitude() == null){
            throw new BadRequestException("Longitude or Latitude not found");
        }
        return weatherService.getWeather(complex.getLatitude(), complex.getLongitude());
    }

    private ComplexResponseDTO mapResponse(Complex complex) {
        return ComplexResponseDTO.builder()
                .id(complex.getId())
                .name(complex.getName())
                .address(complex.getAddress())
                .latitude(complex.getLatitude())
                .longitude(complex.getLongitude())
                .openTime(complex.getOpenTime())
                .closeTime(complex.getCloseTime())
                .build();
    }

    private Complex mapRequest(ComplexRequestDTO complexRequestDTO) {
        return Complex.builder()
                .active(true)
                .address(complexRequestDTO.getAddress())
                .latitude(complexRequestDTO.getLatitude())
                .longitude(complexRequestDTO.getLongitude())
                .name(complexRequestDTO.getName())
                .openTime(complexRequestDTO.getOpenTime())
                .closeTime(complexRequestDTO.getCloseTime())
                .build();
    }
}

package com.nicoferreyra.alangulo.controller;

import com.nicoferreyra.alangulo.dtos.requestDTO.ComplexRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.ComplexResponseDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.WeatherResponseDTO;
import com.nicoferreyra.alangulo.service.ComplexService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Validated
@RestController
@RequestMapping("/complex")
@RequiredArgsConstructor
public class ComplexController {

    private final ComplexService complexService;


    @PostMapping
    public ResponseEntity<ComplexResponseDTO> createComplex(@Valid @RequestBody ComplexRequestDTO complex){
        return new ResponseEntity<>(complexService.createComplex(complex), HttpStatus.CREATED);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ComplexResponseDTO> getComplex(@PathVariable Long id){
        return new ResponseEntity<>(complexService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ComplexResponseDTO>> getAllComplexes(Pageable pageable){

        return new ResponseEntity<>(complexService.getAllComplexes(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplex(@PathVariable Long id){
        complexService.deleteComplex(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping ("/search")
    public ResponseEntity<List <ComplexResponseDTO>>findComplexNearby (@Valid @Min(-90) @Max(90) @RequestParam double lat,
                                                                       @Valid @Min (-180) @Max (180)@RequestParam double lon,
                                                                       @Valid @Min(0) @Max(15) @RequestParam double rad){
        return new ResponseEntity<>(complexService.findNearby(lat, lon, rad), HttpStatus.OK);
    }

    @GetMapping ("/{id}/weather")
    public ResponseEntity<WeatherResponseDTO> getWeatherByComplexId(@PathVariable Long id){
        return  new ResponseEntity<>(complexService.getWeatherByComplexId(id), HttpStatus.OK);
    }

    @GetMapping("/prueba-turbo")
    public String probarTurbo() {
        return "¡El motor se actualizó en vivo sin apagarse!";
    }
}

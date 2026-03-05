package com.nicoferreyra.alangulo.controller;

import com.nicoferreyra.alangulo.dtos.requestDTO.BookingRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.BookingResponseDTO;
import com.nicoferreyra.alangulo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO,
                                                            Authentication authentication) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return new  ResponseEntity<>(bookingService.createBooking(bookingRequestDTO, email), HttpStatus.CREATED);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponseDTO>> getMyBookings(Authentication authentication) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        return new ResponseEntity<>(bookingService.getUserBookings(email), HttpStatus.OK);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<BookingResponseDTO> cancelBooking (@PathVariable Long id,
                                                             Authentication authentication){

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        return new ResponseEntity<>(bookingService.changeStatusBooking(id, email), HttpStatus.OK);
    }
}

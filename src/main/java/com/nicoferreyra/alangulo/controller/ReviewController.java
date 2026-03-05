package com.nicoferreyra.alangulo.controller;

import com.nicoferreyra.alangulo.dtos.requestDTO.ReviewRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.ReviewResponseDTO;
import com.nicoferreyra.alangulo.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody @Valid ReviewRequestDTO reviewRequestDTO,
                                                          Authentication authentication) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        return new ResponseEntity<>(reviewService.createReview(reviewRequestDTO, email), HttpStatus.CREATED);
    }
}

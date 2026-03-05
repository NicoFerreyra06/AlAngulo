package com.nicoferreyra.alangulo.service;

import com.nicoferreyra.alangulo.dtos.requestDTO.ReviewRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.ReviewResponseDTO;
import com.nicoferreyra.alangulo.exceptions.NotFoundException;
import com.nicoferreyra.alangulo.model.Complex;
import com.nicoferreyra.alangulo.model.Review;
import com.nicoferreyra.alangulo.model.User;
import com.nicoferreyra.alangulo.repository.ComplexRepository;
import com.nicoferreyra.alangulo.repository.ReviewRepository;
import com.nicoferreyra.alangulo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final ComplexRepository complexRepository;

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO, String email) {

        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Complex complex = complexRepository.findById(reviewRequestDTO.getComplexId())
                .orElseThrow(() -> new NotFoundException("Complex not found"));

       Review reviewSaved = reviewRepository.save(Review.builder()
               .complex(complex)
               .user(user)
               .comment(reviewRequestDTO.getComment())
               .score(reviewRequestDTO.getScore()).build());

       Double avg = reviewRepository.averageComplexScore(complex.getId());


        return ReviewResponseDTO.builder()
                .avgCourt(avg)
                .comment(reviewSaved.getComment())
                .reviewId(reviewSaved.getId())
                .userId(reviewSaved.getUser().getId())
                .puntuacion(reviewSaved.getScore())
                .build();
    }
}

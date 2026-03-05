package com.nicoferreyra.alangulo.repository;

import com.nicoferreyra.alangulo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT avg(r.score) from Review r where r.complex.id = :idComplex")
    Double averageComplexScore (@Param("idComplex") Long idComplex);
}

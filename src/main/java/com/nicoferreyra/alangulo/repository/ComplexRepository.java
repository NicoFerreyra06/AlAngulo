package com.nicoferreyra.alangulo.repository;

import com.nicoferreyra.alangulo.model.Complex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplexRepository extends JpaRepository<Complex, Long> {
    @Query(value = "SELECT * FROM complex " +
            "WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(latitude)))) < :radius " +
            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(latitude)))) ASC",
            nativeQuery = true)
    List<Complex> findNearby(@Param("lat") Double lat,
                             @Param("lng") Double lng,
                             @Param("radius") Double radius);
}

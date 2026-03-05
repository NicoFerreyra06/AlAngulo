package com.nicoferreyra.alangulo.repository;

import com.nicoferreyra.alangulo.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court,Long> {

    @Query("select c from Court c WHERE c.id not in (" +
            "select b.court.id FROM  Booking b where " +
            "b.date = :date AND b.startTime < :endTime AND b.endTime > :startTime)")
    List<Court> findAvailableCourts (@Param("date") LocalDate date,
                                     @Param("startTime")LocalTime startTime,
                                     @Param("endTime") LocalTime endTime);
}

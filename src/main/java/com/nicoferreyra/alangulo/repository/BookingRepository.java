package com.nicoferreyra.alangulo.repository;

import com.nicoferreyra.alangulo.enums.eStatus;
import com.nicoferreyra.alangulo.model.Booking;
import com.nicoferreyra.alangulo.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query ("SELECT count(b) > 0 from Booking b where b.court = :court AND b.date = :date " +
            "AND (b.startTime < :endTime and b.endTime > :startTime)" +
            "AND b.status = 'CONFIRMED'")
    boolean existsBookingOverlap (@Param("court") Court court,
                                  @Param ("startTime")LocalTime startTime,
                                  @Param ("endTime")LocalTime endTime,
                                  @Param ("date")LocalDate date);

    @Query("SELECT b from Booking b where b.status = :status and " +
            "(b.date < :currentDate or(b.date = :currentDate and b.endTime <= :currentTime))")
    List<Booking> findByStatusInThePast (@Param("currentDate") LocalDate currentDate,
                                         @Param("status") eStatus status,
                                         @Param("currentTime") LocalTime currentTime);

    List<Booking> findByUserId(Long userId);
}

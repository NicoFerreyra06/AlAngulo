package com.nicoferreyra.alangulo.service;

import com.nicoferreyra.alangulo.dtos.requestDTO.BookingRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.BookingResponseDTO;
import com.nicoferreyra.alangulo.enums.eStatus;
import com.nicoferreyra.alangulo.exceptions.BadRequestException;
import com.nicoferreyra.alangulo.exceptions.NotFoundException;
import com.nicoferreyra.alangulo.model.Booking;
import com.nicoferreyra.alangulo.model.Complex;
import com.nicoferreyra.alangulo.model.Court;
import com.nicoferreyra.alangulo.model.User;
import com.nicoferreyra.alangulo.repository.BookingRepository;
import com.nicoferreyra.alangulo.repository.CourtRepository;
import com.nicoferreyra.alangulo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CourtRepository courtRepository;
    private final UsersRepository  usersRepository;
    private final EmailService emailService;

    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, String email) {

        if (!bookingRequestDTO.getEndTime().isAfter(bookingRequestDTO.getStartTime())) {
            throw new BadRequestException("End Time must be after Start Time");
        }

        if (Duration.between(bookingRequestDTO.getStartTime(), bookingRequestDTO.getEndTime()).toMinutes() < 60) {
            throw new BadRequestException("The minimum rental period is one hour.");
        }

        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Court court = courtRepository.findById(bookingRequestDTO.getCourtId())
                .orElseThrow(()-> new NotFoundException("Court not found"));

        Complex complex = court.getComplex();
        if (bookingRequestDTO.getStartTime().isBefore(complex.getOpenTime())
                || bookingRequestDTO.getEndTime().isAfter(complex.getCloseTime())) {
            throw new BadRequestException("The resort's opening hours are: " +
                    " Open: " + complex.getOpenTime() +
                    " Close: " + complex.getCloseTime() +
                    " please enter a correct booking time.");
        }
        Long minutes = Duration.between(bookingRequestDTO.getStartTime(), bookingRequestDTO.getEndTime()).toMinutes();
        BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        if (bookingRepository.existsBookingOverlap(
                court,
                bookingRequestDTO.getStartTime(),
                bookingRequestDTO.getEndTime(),
                bookingRequestDTO.getDate())){

            throw new BadRequestException("The court is occupied at that time.");
        }

        Booking bookingSaved = bookingRepository.save(Booking.builder()
                    .price(hours.multiply(court.getPrice()))
                    .user(user)
                    .court(court)
                    .startTime(bookingRequestDTO.getStartTime())
                    .endTime(bookingRequestDTO.getEndTime())
                    .date(bookingRequestDTO.getDate())
                    .status(eStatus.CONFIRMED)
                    .build());

        String cuerpo = String.format(
                "¡Hola! Tu reserva en Al Angulo está confirmada. ⚽\n\n" +
                        "------------------------------------------\n" +
                        "       DETALLE DE TU TURNO\n" +
                        "------------------------------------------\n" +
                        "Fecha:      %s\n" +
                        "Horario:    %s a %s\n" +
                        "Complejo:   %s\n" +
                        "Cancha:     %s\n" +
                        "Precio:     $%s\n" +
                        "------------------------------------------\n\n" +
                        "Te esperamos 10 minutos antes. ¡No te olvides las canilleras!\n\n" +
                        "Si necesitás cancelar, recordá hacerlo con 12 horas de anticipación.",
                bookingSaved.getDate(),
                bookingSaved.getStartTime(),
                bookingSaved.getEndTime(),
                bookingSaved.getCourt().getComplex().getName(),
                bookingSaved.getCourt().getName(),
                bookingSaved.getPrice()
        );

        emailService.sendEmail(user.getEmail(), "✅ Reserva Confirmada - Al Angulo ⚽", cuerpo);

        return BookingResponseDTO.builder()
                .bookingId(bookingSaved.getId())
                .date(bookingSaved.getDate())
                .startTime(bookingSaved.getStartTime())
                .endTime(bookingSaved.getEndTime())
                .nameComplex(bookingSaved.getCourt().getComplex().getName())
                .bookingStatus(bookingSaved.getStatus() )
                .nameCourt(bookingSaved.getCourt().getTypeCourt().toString())
                .price(bookingSaved.getPrice())
                .build();
    }

    public List<BookingResponseDTO> getUserBookings(String email) {

        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserId(user.getId());

        if (bookings.isEmpty()) {
            return List.of();
        }

        return bookings.stream().map(booking -> BookingResponseDTO.builder()
                .price(booking.getPrice())
                .bookingId(booking.getId())
                .nameCourt(booking.getCourt().getName())
                .nameComplex(booking.getCourt().getComplex().getName())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .bookingStatus(booking.getStatus()).build()).toList();

    }

    public BookingResponseDTO changeStatusBooking(Long bookingId, String email){

        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("The reservation does not belong to him.");
        }

        if (booking.getStatus().equals(eStatus.CANCELED)) {
            throw new BadRequestException("The reservation is already cancelled.");
        }

        LocalDateTime gameTime = LocalDateTime.of(booking.getDate(), booking.getStartTime());

        long hoursUntilGame = Duration.between(LocalDateTime.now(), gameTime).toHours();

        if (hoursUntilGame < 12){
            throw new BadRequestException("You cannot cancel less than 12 hours in advance.");
        }

        booking.setStatus(eStatus.CANCELED);
        Booking bookingSaved = bookingRepository.save(booking);

        return BookingResponseDTO.builder()
                .bookingId(bookingSaved.getId())
                .price(bookingSaved.getPrice())
                .bookingId(bookingSaved.getId())
                .date(bookingSaved.getDate())
                .startTime(bookingSaved.getStartTime())
                .endTime(bookingSaved.getEndTime())
                .bookingStatus(bookingSaved.getStatus())
                .nameComplex(bookingSaved.getCourt().getComplex().getName())
                .build();
    }

    @Scheduled(fixedRate = 3600000)
    public void checkBooking(){

        List<Booking> bookings = bookingRepository.findByStatusInThePast(
                LocalDate.now(),
                eStatus.CONFIRMED,
                LocalTime.now());

        for (Booking booking : bookings) {
            booking.setStatus(eStatus.FINALIZED);
        }
        bookingRepository.saveAll(bookings);
    }
}

package com.nicoferreyra.alangulo;

import com.nicoferreyra.alangulo.dtos.requestDTO.BookingRequestDTO;
import com.nicoferreyra.alangulo.dtos.responseDTO.BookingResponseDTO;
import com.nicoferreyra.alangulo.enums.eRol;
import com.nicoferreyra.alangulo.enums.eTypeCourt;
import com.nicoferreyra.alangulo.exceptions.BadRequestException;
import com.nicoferreyra.alangulo.model.Booking;
import com.nicoferreyra.alangulo.model.Complex;
import com.nicoferreyra.alangulo.model.Court;
import com.nicoferreyra.alangulo.model.User;
import com.nicoferreyra.alangulo.repository.BookingRepository;
import com.nicoferreyra.alangulo.repository.CourtRepository;
import com.nicoferreyra.alangulo.repository.UsersRepository;
import com.nicoferreyra.alangulo.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private CourtRepository courtRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_ShouldThrowException_WhenEndTimeBeforeStartTime (){

        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setCourtId(1L);
        bookingRequestDTO.setDate(LocalDate.now());
        bookingRequestDTO.setStartTime(LocalTime.of(20, 00));
        bookingRequestDTO.setEndTime(LocalTime.of(10, 00));

        assertThrows(BadRequestException.class,
                () -> {
                    bookingService.createBooking(bookingRequestDTO, "nico@gmail");
                });
        Mockito.verify(bookingRepository, Mockito.never()).save(Mockito.any(Booking.class));
    }

    @Test
    void createBookingSuccessful(){
        Complex complex = new Complex();
        complex.setName("Punto sur");
        complex.setAddress("Maradona 1545");
        complex.setLatitude(90);
        complex.setLongitude(180);
        complex.setActive(true);
        complex.setId(1L);

        Court court = new Court();
        court.setId(1L);
        court.setComplex(complex);
        court.setPrice(BigDecimal.valueOf(5000));
        court.setTypeCourt(eTypeCourt.FUTBOL11);

        User user = new User();
        user.setId(1L);
        user.setNombre("Nico");
        user.setPhoto("https");
        user.setEmail("nico@gmail");
        user.setRol(eRol.USER);

        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setCourtId(court.getId());
        bookingRequestDTO.setDate(LocalDate.of(2026, 1, 21));
        bookingRequestDTO.setStartTime(LocalTime.of(11, 00));
        bookingRequestDTO.setEndTime(LocalTime.of(12, 00));

        when(usersRepository.findByEmail("nico@gmail")).thenReturn(Optional.of(user));
        when(courtRepository.findById(court.getId())).thenReturn(Optional.of(court));
        when(bookingRepository.existsBookingOverlap(court,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0), LocalDate.of(2026, 1, 21))).thenReturn(false);

        when(bookingRepository.save(Mockito.any(Booking.class)))
                .thenAnswer(invocation ->{
                    Booking booking = invocation.getArgument(0);
                    booking.setId(1L);
                    return booking;
                });

        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(bookingRequestDTO, "nico@gmail");

        verify(bookingRepository, Mockito.times(1)).save(Mockito.any(Booking.class));

        assertEquals (court.getComplex().getName(), bookingResponseDTO.getNameComplex());
        assertEquals(court.getPrice(), bookingResponseDTO.getPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
    }
}

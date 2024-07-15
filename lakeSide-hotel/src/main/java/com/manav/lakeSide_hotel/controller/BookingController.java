package com.manav.lakeSide_hotel.controller;


import com.manav.lakeSide_hotel.exception.InvalidBookingRequestException;
import com.manav.lakeSide_hotel.exception.ResourceNotFoundException;
import com.manav.lakeSide_hotel.model.BookedRoom;
import com.manav.lakeSide_hotel.model.Room;
import com.manav.lakeSide_hotel.response.BookingResponse;
import com.manav.lakeSide_hotel.response.RoomResponse;
import com.manav.lakeSide_hotel.service.IBookingService;
import com.manav.lakeSide_hotel.service.IRoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4000"})
public class BookingController {

    private final IBookingService bookingService;

    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }

        return ResponseEntity.ok(bookingResponses);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(

        );

        return new BookingResponse(
                booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(), booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getNumOfAdults(),
                booking.getBookingConfirmationCode(), room
        );
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest){
        try {
            String confirmationCode = bookingService.saveBooking(roomId,bookingRequest);
            return ResponseEntity.ok("Room Booked Successfully ! Your Booking Confirmation Code is : " + confirmationCode);
        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }
}

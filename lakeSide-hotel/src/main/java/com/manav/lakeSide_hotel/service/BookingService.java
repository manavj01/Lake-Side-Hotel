package com.manav.lakeSide_hotel.service;

import com.manav.lakeSide_hotel.model.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService{

    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return null;
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return List.of();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        return "";
    }

    @Override
    public void cancelBooking(Long bookingId) {

    }
}

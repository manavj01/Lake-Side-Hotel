package com.manav.lakeSide_hotel.service;

import com.manav.lakeSide_hotel.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);
}

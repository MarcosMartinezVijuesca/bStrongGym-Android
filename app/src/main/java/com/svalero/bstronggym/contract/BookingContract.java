package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.domain.Booking;
import java.util.List;

public interface BookingContract {

    interface View {
        void onBookingsLoaded(List<Booking> bookings);
        void onBookingSaved();
        void onBookingDeleted();
        void onError(String message);
    }

    interface Presenter {
        void loadBookings();
        void loadBookingsByMember(long memberId);
        void saveBooking(Booking booking);
        void updateBooking(long id, Booking booking);
        void deleteBooking(long id);
    }
}

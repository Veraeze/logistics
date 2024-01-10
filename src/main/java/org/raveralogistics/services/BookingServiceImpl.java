package org.raveralogistics.services;

import org.raveralogistics.data.model.Booking;
import org.raveralogistics.data.model.Customer;
import org.raveralogistics.data.model.Sender;
import org.raveralogistics.data.repository.BookingRepository;
import org.raveralogistics.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Booking book(String bookingId, Sender senderInfo, Customer receiverInfo, String userId, String parcelName, LocalDateTime dateTime) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setSenderInfo(senderInfo);
        booking.setReceiverInfo(receiverInfo);
        booking.setParcelName(parcelName);
        booking.setDateTime(dateTime);
        booking.setUserId(userId);

        booking.setBooked(true);

        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}

package org.raveralogistics.services;

import org.raveralogistics.data.model.Booking;
import org.raveralogistics.data.model.Customer;
import org.raveralogistics.data.model.Sender;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking book( String bookingId, Sender senderInfo, Customer receiverInfo, String userId, String parcelName, LocalDateTime dateTime);
    List<Booking> findAll();

}

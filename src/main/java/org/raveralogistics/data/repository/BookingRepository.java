package org.raveralogistics.data.repository;

import org.raveralogistics.data.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
    Booking findBookingByBookingId(String id);
}

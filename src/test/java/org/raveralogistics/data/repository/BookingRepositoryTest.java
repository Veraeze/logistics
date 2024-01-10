package org.raveralogistics.data.repository;

import org.junit.jupiter.api.Test;
import org.raveralogistics.data.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingRepositoryTest {
    @Autowired
    BookingRepository booking;

    @Test
    void countIsOneWhenYouSaveOne(){
        Booking book = new Booking();
        booking.save(book);
        assertEquals(1, booking.count());
    }
}
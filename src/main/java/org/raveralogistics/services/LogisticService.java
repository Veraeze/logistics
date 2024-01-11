package org.raveralogistics.services;

import org.raveralogistics.data.model.Booking;
import org.raveralogistics.data.model.Feedback;
import org.raveralogistics.data.model.User;
import org.raveralogistics.dtos.request.*;

import java.math.BigDecimal;

public interface LogisticService {
    User register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    User findAccountBelongingTo(String name);

    void logout(LoginRequest loginRequest);

    void depositMoneyIntoWallet(DepositMoneyRequest depositMoneyRequest);

    void withdrawMoneyFromWallet(String userId, BigDecimal bigDecimal);

    Booking bookService(BookingRequest bookingRequest);

    Feedback addFeedback(FeedbackRequest feedbackRequest);
}

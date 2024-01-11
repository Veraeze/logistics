package org.raveralogistics.services;

import org.raveralogistics.data.model.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback feedback(String feedbackId, String userId, String bookingId, String feedback);
    List<Feedback> findAll();
}

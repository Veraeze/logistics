package org.raveralogistics.data.repository;

import org.raveralogistics.data.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    Feedback findFeedbackByFeedbackId(String feedbackId);

}

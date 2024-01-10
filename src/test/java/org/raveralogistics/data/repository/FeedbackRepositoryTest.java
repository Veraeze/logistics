package org.raveralogistics.data.repository;

import org.junit.jupiter.api.Test;
import org.raveralogistics.data.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedbackRepositoryTest {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    void countIsOneWhenYouSaveOne(){
        Feedback feedback = new Feedback();
        feedbackRepository.save(feedback);
        assertEquals(1, feedbackRepository.count());
    }

}
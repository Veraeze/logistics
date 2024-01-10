package org.raveralogistics.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Feedback {

    @Id
    private String feedbackId;
    private String userId;
    private String bookingId;
    private String feedBack;

}

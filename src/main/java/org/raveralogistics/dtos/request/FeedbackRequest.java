package org.raveralogistics.dtos.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String userId;
    private String bookingId;
    private String feedBack;
}

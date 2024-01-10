package org.raveralogistics.dtos.request;

import lombok.Data;
import org.raveralogistics.data.model.Customer;
import org.raveralogistics.data.model.User;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private User senderInfo;
    private Customer receiverInfo;
    private  boolean isDelivered;
    private String parcelName;
    private LocalDateTime date;
}

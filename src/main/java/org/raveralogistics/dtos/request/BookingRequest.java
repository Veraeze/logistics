package org.raveralogistics.dtos.request;

import lombok.Data;
import org.raveralogistics.data.model.Customer;
import org.raveralogistics.data.model.Sender;

import java.math.BigDecimal;


@Data
public class BookingRequest {
    private Sender senderInfo;
    private Customer receiverInfo;
    private  String userId;
    private String parcelName;
    private BigDecimal cost = BigDecimal.ZERO;
}

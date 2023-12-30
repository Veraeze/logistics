package org.raveralogistics.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Booking {

    @Id
    private String bookingId;
    private User senderInfo;
    private Customer receiverInfo;
    private  boolean isDelivered;
    private String parcelName;
    private LocalDateTime date;

}

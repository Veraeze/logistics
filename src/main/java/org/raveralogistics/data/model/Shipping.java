package org.raveralogistics.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Shipping {

    @Id
    private String shippingId;
    private String bookingId;
    private boolean isDelivered;


    public Shipping(String shippingId, boolean isDelivered) {
        this.shippingId = shippingId;
        this.isDelivered = isDelivered;
    }
}

package org.raveralogistics.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Wallet {

    private String cardNumber;
    private String threeDigitNumber;
    private String userId;
    private BigDecimal balance = BigDecimal.ZERO;

}

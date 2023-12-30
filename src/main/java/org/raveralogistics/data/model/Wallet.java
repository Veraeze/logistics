package org.raveralogistics.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wallet {

    private String cardNumber;
    private String threeDigitNumber;
    private User userInfo;
    private Account account;

}

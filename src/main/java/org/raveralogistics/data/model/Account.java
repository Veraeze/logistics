package org.raveralogistics.data.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private String accountName;
    private String accountNumber;
    private String pin;
    private BigDecimal balance;


    public Account(String accountName, String accountNumber, String pin) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.pin = pin;
    }
}

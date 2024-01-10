package org.raveralogistics.data.model;

import lombok.Data;

@Data
public class Sender {
    private String name;
    private  String phoneNumber;
    private String emailAddress;
    private Address homeAddress;
}

package org.raveralogistics.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

    private String name;
    private String phoneNumber;
    private String email;
    private Address homeAddress;

}

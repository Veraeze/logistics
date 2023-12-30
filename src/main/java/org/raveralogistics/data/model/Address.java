package org.raveralogistics.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Address {

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}

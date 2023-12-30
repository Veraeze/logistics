package org.raveralogistics.dtos.request;

import lombok.Data;
import org.raveralogistics.data.model.Address;

@Data
public class RegisterRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Address homeAddress;
}

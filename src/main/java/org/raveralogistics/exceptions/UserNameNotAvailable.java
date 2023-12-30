package org.raveralogistics.exceptions;

public class UserNameNotAvailable extends RuntimeException{
    public UserNameNotAvailable(String message){
        super(message);
    }
}

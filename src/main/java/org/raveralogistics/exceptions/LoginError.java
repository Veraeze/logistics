package org.raveralogistics.exceptions;

public class LoginError extends RuntimeException {
    public LoginError(String message) {
        super(message);
    }
}

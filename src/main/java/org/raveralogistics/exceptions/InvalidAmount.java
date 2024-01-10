package org.raveralogistics.exceptions;

public class InvalidAmount extends RuntimeException {
    public InvalidAmount(String message) {
        super(message);
    }
}

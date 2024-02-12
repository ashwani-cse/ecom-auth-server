package com.management.user.exception;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
package com.management.user.exception;

import com.management.user.dto.ErrorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Slf4j
public final class ErrorResponse {

    public static ResponseEntity<ErrorPayload> handlerException(int statusCode, String errorMessage) {
        ErrorPayload errorPayload = new ErrorPayload();
        errorPayload.setStatusCode(statusCode);
        errorPayload.setMessage(errorMessage);
        log.error("handlerException: Error response returned: {}", errorPayload);
        return new ResponseEntity<>(errorPayload, HttpStatus.valueOf(statusCode));
    }
}
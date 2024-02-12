package com.management.user.exception;

import com.management.user.dto.ErrorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import static com.management.user.exception.ErrorResponse.handlerException;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorPayload> handleCustomException(CustomException exception) {
        log.error("handleCustomException: {}", exception.getMessage());
        return handlerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorPayload> handleBadRequestException(RestClientResponseException exception) {
        log.error("handleBadRequestException: {}", exception.getMessage());
        return handlerException(exception.getStatusCode().value(), exception.getMessage());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorPayload> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("handleBadCredentialsException: {}", exception.getMessage());
        return handlerException(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorPayload> handleObjectNotFoundException(ObjectNotFoundException exception) {
        log.error("handleObjectNotFoundException: {}", exception.getMessage());
        return handlerException(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

}
